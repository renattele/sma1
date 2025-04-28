package com.team6.smartbudget.core.presentation.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.team6.smartbudget.core.presentation.designsystem.theme.ProvideTextStyleAndColor
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.util.PhoneVisualTransformation
import com.team6.smartbudget.core.presentation.util.SeparatorEveryThreeVisualTransformation
import com.team6.smartbudget.core.presentation.util.runWhenNotNull
import com.team6.smartbudget.sma1.R

@Composable
fun TTextField(
    action: TTextFieldAction,
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
) {
    OutlinedTextField(
        value = action.value,
        onValueChange = action.onValueChange,
        modifier = modifier.runWhenNotNull(action.onClick) { onClick ->
            pointerInput(Unit) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        onClick()
                    }
                }
            }
        },
        textStyle = TTheme.typography.bodyM,
        shape = TTheme.shape.m,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TTheme.colorScheme.primary,
            focusedTextColor = TTheme.colorScheme.onBackground,
            unfocusedTextColor = TTheme.colorScheme.onBackground,
            unfocusedBorderColor = TTheme.colorScheme.onBackgroundMedium,
            errorTextColor = TTheme.colorScheme.error,
            errorSupportingTextColor = TTheme.colorScheme.error,
            errorLabelColor = TTheme.colorScheme.error,
            errorBorderColor = TTheme.colorScheme.error,
            errorCursorColor = TTheme.colorScheme.error,
        ),
        label = {
            ProvideInnerStyle {
                label?.invoke()
            }
        },
        supportingText = {
            ProvideInnerStyle {
                supportingText?.invoke()
            }
        },
        trailingIcon = {
            ProvideInnerStyle {
                action.trailingIcon?.invoke()
            }
        },
        enabled = enabled,
        visualTransformation = action.visualTransformation,
        keyboardOptions = action.keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        isError = action.isError,
        readOnly = action.readOnly,
    )
}

@Composable
private fun ProvideInnerStyle(content: @Composable () -> Unit) {
    ProvideTextStyleAndColor(
        TTheme.typography.bodyS,
        TTheme.colorScheme.onBackgroundMedium,
        content = content,
    )
}

/**
 * Action description for [TTextField]. It describes different types of inputs:
 * - Text
 * - Number
 * - Date
 * - your own
 *
 * Every action should start with uppercase because it can produce UI.
 */
data class TTextFieldAction(
    val value: String,
    val onValueChange: (String) -> Unit,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val trailingIcon: @Composable (() -> Unit)? = null,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val isError: Boolean = false,
    val readOnly: Boolean = false,
    val onClick: (() -> Unit)? = null,
) {
    companion object
}

@Composable
fun TTextFieldAction.Companion.Text(
    value: String,
    onValueChange: (String) -> Unit,
) = TTextFieldAction(
    value = value,
    onValueChange = onValueChange,
    trailingIcon = {
        Icon(
            Icons.Default.Close,
            contentDescription = null,
            Modifier.clickable {
                onValueChange("")
            },
        )
    },
)

@Composable
fun TTextFieldAction.Companion.Password(
    value: String,
    onValueChange: (String) -> Unit,
): TTextFieldAction {
    val transformation = remember { PasswordVisualTransformation() }
    return Text(value, onValueChange)
        .copy(visualTransformation = transformation)
}

@Composable
fun TTextFieldAction.Companion.Number(
    value: Long?,
    onValueChange: (Long?) -> Unit,
): TTextFieldAction {
    var internalValue by remember(value) { mutableStateOf(value?.toString() ?: "") }
    val isError = internalValue.toLongOrNull() == null
    return TTextFieldAction(
        internalValue,
        onValueChange = {
            internalValue = it
            val updated = it.toLongOrNull()
            if (updated != null) {
                onValueChange(updated)
            } else {
                onValueChange(null)
            }
        },
        visualTransformation = SeparatorEveryThreeVisualTransformation(),
        isError = isError,
    )
}

@Composable
fun TTextFieldAction.Companion.Phone(
    value: String,
    onValueChange: (String?) -> Unit,
): TTextFieldAction {
    val maskStyle = TTheme.typography.bodyM.copy(color = TTheme.colorScheme.onBackgroundLow)
    val transformation = remember { PhoneVisualTransformation(maskStyle) }
    val state = remember { PhoneTextFieldState(value) }

    return TTextFieldAction(
        value = state.value,
        onValueChange = { newValue -> state.updateValue(newValue, onValueChange) },
        visualTransformation = transformation,
        isError = state.isError,
    )
}

private class PhoneTextFieldState(
    initialValue: String = "",
) {
    var value by mutableStateOf(initialValue)
        private set

    val isError: Boolean
        get() = !value.matches(PhoneRegex)

    fun updateValue(
        newValue: String,
        onValueChange: (String?) -> Unit,
    ) {
        val filteredValue = newValue
            .filter { it.isDigit() }
            .take(PhoneVisualTransformation.MAX_PHONE_DIGITS)
        value = filteredValue

        if (value.matches(PhoneRegex)) {
            onValueChange(filteredValue)
        } else {
            onValueChange(null)
        }
    }

    companion object {
        private val PhoneRegex = "[0-9]{0,10}$".toRegex()
    }
}

@Composable
fun TTextFieldAction.Companion.Selector(
    value: String,
    onClick: () -> Unit,
): TTextFieldAction = TTextFieldAction(
    value = value,
    onValueChange = {},
    readOnly = true,
    onClick = onClick,
    trailingIcon = {
        Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = null)
    },
)

private class TTextFieldActionProvider :
    PreviewParameterProvider<@Composable () -> TTextFieldAction> {
    override val values: Sequence<@Composable () -> TTextFieldAction>
        get() = sequenceOf(
            {
                var value by remember { mutableStateOf("") }
                TTextFieldAction.Text(value) { value = it }
            },
            {
                var value by remember { mutableStateOf("") }
                TTextFieldAction.Password(value) { value = it }
            },
            {
                var value by remember { mutableStateOf(0L) }
                TTextFieldAction.Number(value) { if (it != null) value = it }
            },
            {
                var value by remember { mutableStateOf<String>("") }
                TTextFieldAction.Phone(value) { if (it != null) value = it }
            },
            {
                TTextFieldAction.Selector(stringResource(R.string.label_done)) {}
            },
        )
}

@PreviewLightDark
@Composable
private fun TTextFieldPreview(
    @PreviewParameter(TTextFieldActionProvider::class) action: @Composable () -> TTextFieldAction,
) {
    TPreviewTheme {
        TTextField(
            action = action(),
            label = {
                Text(stringResource(R.string.label_done))
            },
            supportingText = {
                Text(stringResource(R.string.label_recommended_goal))
            },
        )
    }
}

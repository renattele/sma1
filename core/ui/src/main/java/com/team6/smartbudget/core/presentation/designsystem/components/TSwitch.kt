package com.team6.smartbudget.core.presentation.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.util.BooleanPreviewParameterProvider

private const val DisabledSwitchAlpha = 0.5f

@Composable
fun TSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = TTheme.colorScheme.primary,
    enabled: Boolean = true,
) {
    Switch(
        checked,
        onCheckedChange,
        modifier,
        enabled = enabled,
        colors = tSwitchColors(color),
        thumbContent = {
            val icon = if (checked) {
                Icons.Outlined.Check
            } else {
                Icons.Outlined.Close
            }
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(
                    SwitchDefaults.IconSize,
                ),
            )
        },
    )
}

@Composable
private fun tSwitchColors(color: Color): SwitchColors {
    val thumbColor = TTheme.colorScheme.background
    val borderColor = Color.Transparent
    val iconColor = TTheme.colorScheme.onBackground
    return SwitchDefaults.colors(
        checkedTrackColor = color,
        checkedThumbColor = thumbColor,
        checkedBorderColor = borderColor,
        checkedIconColor = iconColor,
        uncheckedTrackColor = TTheme.colorScheme.secondary,
        uncheckedThumbColor = thumbColor,
        uncheckedBorderColor = borderColor,
        uncheckedIconColor = iconColor,
        disabledCheckedTrackColor = color.copy(alpha = DisabledSwitchAlpha),
        disabledCheckedThumbColor = thumbColor,
        disabledCheckedBorderColor = borderColor,
        disabledCheckedIconColor = iconColor.copy(alpha = DisabledSwitchAlpha),
        disabledUncheckedTrackColor = TTheme.colorScheme.secondary,
        disabledUncheckedThumbColor = thumbColor,
        disabledUncheckedBorderColor = borderColor,
        disabledUncheckedIconColor = iconColor.copy(alpha = DisabledSwitchAlpha),
    )
}

@PreviewLightDark
@Composable
private fun TSwitchPreview(
    @PreviewParameter(BooleanPreviewParameterProvider::class) isChecked: Boolean,
) {
    TPreviewTheme {
        TSwitch(
            isChecked,
            onCheckedChange = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun TSwitchPreviewDisabled(
    @PreviewParameter(BooleanPreviewParameterProvider::class) isChecked: Boolean,
) {
    TPreviewTheme {
        TSwitch(
            isChecked,
            onCheckedChange = {},
            enabled = false,
        )
    }
}

package com.team6.smartbudget.core.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.team6.smartbudget.core.presentation.designsystem.theme.ProvideTextStyleAndColor
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.bold
import com.team6.smartbudget.core.presentation.util.runWhenNotNull
import com.team6.smartbudget.sma1.R

private const val DisabledOpacity = 0.56f

@Composable
fun TButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: TButtonStyle = TButtonStyle.primary(),
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier
            .runWhenNotNull(style.border) { stroke ->
                border(
                    stroke,
                    shape = TTheme.shape.m,
                )
            }.clip(TTheme.shape.m)
            .clickable(enabled = enabled, onClick = onClick)
            .background(
                style.backgroundColor.copy(
                    alpha = if (enabled) 1f else DisabledOpacity,
                ),
            ).padding(TTheme.spacing.m),
        horizontalArrangement = Arrangement.spacedBy(
            TTheme.spacing.s,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProvideTextStyleAndColor(
            textStyle = TTheme.typography.bodyM.bold(),
            color = style.contentColor,
        ) {
            content()
        }
    }
}

data class TButtonStyle(
    val backgroundColor: Color,
    val contentColor: Color,
    val border: BorderStroke?,
) {
    companion object {
        @Composable
        fun primary(): TButtonStyle = TButtonStyle(
            backgroundColor = TTheme.colorScheme.primary,
            contentColor = TTheme.colorScheme.onPrimary,
            border = null,
        )

        @Composable
        fun secondary(): TButtonStyle = TButtonStyle(
            backgroundColor = TTheme.colorScheme.secondary,
            contentColor = TTheme.colorScheme.onSecondary,
            border = null,
        )

        @Composable
        fun outlined(): TButtonStyle = TButtonStyle(
            backgroundColor = TTheme.colorScheme.background,
            contentColor = TTheme.colorScheme.onBackground,
            border = BorderStroke(
                TTheme.spacing.xxs,
                TTheme.colorScheme.outline,
            ),
        )

        @Composable
        fun error(): TButtonStyle = TButtonStyle(
            backgroundColor = TTheme.colorScheme.error,
            contentColor = TTheme.colorScheme.onError,
            border = null,
        )

        @Composable
        fun flat(): TButtonStyle = TButtonStyle(
            backgroundColor = TTheme.colorScheme.background,
            contentColor = TTheme.colorScheme.onBackground,
            border = null,
        )
    }
}

private class StyleProvider : PreviewParameterProvider<@Composable () -> TButtonStyle> {
    override val values: Sequence<@Composable (() -> TButtonStyle)>
        get() = sequenceOf(
            { TButtonStyle.primary() },
            { TButtonStyle.secondary() },
            { TButtonStyle.outlined() },
            { TButtonStyle.error() },
            { TButtonStyle.flat() },
        )
}

@PreviewLightDark
@Composable
private fun TButtonPreview(
    @PreviewParameter(StyleProvider::class) style: @Composable () -> TButtonStyle,
) {
    TPreviewTheme {
        TButton(
            onClick = {},
            Modifier
                .fillMaxWidth(),
            style = style(),
        ) {
            Icon(Icons.Outlined.Delete, contentDescription = null)
            Text(stringResource(R.string.label_delete))
        }
    }
}

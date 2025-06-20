package com.team6.smartbudget.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.team6.smartbudget.core.ui.R
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme

@Composable
fun TCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    TCard(
        onClick = {},
        modifier = modifier,
        clickEnabled = false,
        content = content,
    )
}

@Composable
fun TCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    clickEnabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier
            .shadow(
                TTheme.spacing.xs,
                TTheme.shape.xl,
                ambientColor = TTheme.colorScheme.onBackgroundHigh,
                spotColor = TTheme.colorScheme.onBackgroundHigh,
            )
            .clip(TTheme.shape.xl)
            .clickable(enabled = clickEnabled, onClick = onClick)
            .background(TTheme.colorScheme.background)
            .padding(TTheme.spacing.l),
    ) {
        content()
    }
}

@PreviewLightDark
@Composable
private fun TCardPreview() {
    TPreviewTheme {
        TCard(
            Modifier
                .fillMaxWidth(),
        ) {
            val action = TTextFieldAction.Selector(
                stringResource(R.string.label_done),
            ) { }
            TTextField(action)
            TButton(onClick = {}, Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.label_done))
            }
        }
    }
}

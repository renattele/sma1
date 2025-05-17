package com.team6.smartbudget.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.team6.smartbudget.core.ui.R
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme

private val SeparatorWidth = 1.dp

@Composable
fun TVerticalSeparator(
    modifier: Modifier = Modifier,
    color: Color = TTheme.colorScheme.onBackgroundLow,
) {
    Box(
        modifier
            .clip(CircleShape)
            .background(color)
            .height(SeparatorWidth)
            .fillMaxWidth(),
    )
}

@PreviewLightDark
@Composable
private fun TSeparatorPreview() {
    TPreviewTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(TTheme.spacing.xs),
        ) {
            TButton(onClick = {}) {
                Text(stringResource(R.string.label_done))
            }
            TVerticalSeparator()
            TButton(onClick = {}) {
                Text(stringResource(R.string.label_done))
            }
        }
    }
}

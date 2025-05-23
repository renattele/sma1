package com.team6.smartbudget.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme

@Composable
fun OfflineCard(modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(TTheme.colorScheme.warning),
    ) {
    }
}

@Preview
@Composable
private fun OfflineCardPreview() {
    TTheme {
        OfflineCard()
    }
}

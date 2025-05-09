package com.team6.smartbudget.core.presentation.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme

@Composable
fun TBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TIconButton(onClick, Icons.AutoMirrored.Filled.KeyboardArrowLeft, modifier)
}

@Composable
fun TForwardButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TIconButton(onClick, Icons.AutoMirrored.Filled.KeyboardArrowRight, modifier)
}

@Composable
private fun TIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    TButton(onClick, modifier, style = TButtonStyle.flat()) {
        Icon(
            icon,
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun TBackButtonPreview() {
    TTheme {
        TBackButton(onClick = { })
    }
}

@Preview
@Composable
private fun TForwardButtonPreview() {
    TTheme {
        TForwardButton(onClick = { })
    }
}

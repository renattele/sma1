package com.team6.smartbudget.features.categories.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme

private val CategoryIconSize = 24.dp

@Composable
fun CategoryIcon(
    icon: Any?,
    color: Int,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        icon.toString().toIntOrNull() ?: icon,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .background(Color(color))
            .padding(TTheme.spacing.m)
            .size(CategoryIconSize),
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.tint(TTheme.colorScheme.onError),
    )
}

@Preview
@Composable
private fun CategoryIconPreview() {
    TTheme {
        CategoryIcon(
            icon = "https://www.svgrepo.com/show/490311/restaurant-waiter.svg",
            color = TTheme.colorScheme.primary.toArgb(),
        )
    }
}

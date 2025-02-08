package com.renattele.sma1.presentation.screen.wallpaper.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.renattele.sma1.R
import com.renattele.sma1.domain.wallpaper.WallpaperEntity
import com.renattele.sma1.presentation.theme.BoxedAppTheme
import com.renattele.sma1.presentation.util.uriFor
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WallpaperCard(
    wallpaper: WallpaperEntity,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(12),
) {
    Column(
        modifier
            .border(1.dp, MaterialTheme.colorScheme.outline, shape)
            .clip(shape)
            .combinedClickable(onLongClick = onLongClick, onClick = onClick)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        AsyncImage(
            if (LocalInspectionMode.current) R.drawable.ic_launcher_background else wallpaper.pictureUri,
            contentDescription = null,
            Modifier
                .padding(bottom = 12.dp)
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            wallpaper.name,
            Modifier
                .padding(bottom = 8.dp)
                .padding(horizontal = 12.dp),
            style = MaterialTheme.typography.titleLarge
        )
        if (wallpaper.description != null) {
            Text(
                wallpaper.description,
                Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun WallpaperCardPreview() {
    val context = LocalContext.current
    BoxedAppTheme {
        WallpaperCard(
            WallpaperEntity(
                id = UUID.randomUUID(),
                author = UUID.randomUUID(),
                name = "Wallpaper",
                description = "Description, Description, Description, Description, Description, Description",
                pictureUri = context.uriFor(R.drawable.ic_launcher_background).toString()
            ),
            modifier = Modifier.width(130.dp)
        )
    }
}
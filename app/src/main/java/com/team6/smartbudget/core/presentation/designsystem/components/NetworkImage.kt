package com.team6.smartbudget.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.size.Scale
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.util.shimmer
import com.team6.smartbudget.sma1.R

@Composable
fun NetworkImage(
    builder: ImageRequest.Builder.() -> ImageRequest.Builder,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    clipToBounds: Boolean = true,
) {
    SubcomposeAsyncImage(
        ImageRequest.Builder(LocalContext.current)
            .error(R.drawable.im_track_thumbnail_fallback)
            .scale(Scale.FILL)
            .crossfade(true)
            .builder()
            .build(),
        contentDescription = contentDescription,
        modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
        clipToBounds = clipToBounds,
        loading = {
            Box(Modifier.shimmer())
        },
    )
}

private const val DefaultThumbnailUrl =
    "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png"

@Preview
@Composable
private fun NetworkImagePreview() {
    TPreviewTheme {
        NetworkImage({
            data(
                DefaultThumbnailUrl,
            )
        }, contentDescription = null)
    }
}

package com.team6.smartbudget.features.overview.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.team6.smartbudget.core.domain.TrackSummaryEntity
import com.team6.smartbudget.core.presentation.designsystem.components.NetworkImage
import com.team6.smartbudget.core.presentation.designsystem.components.TCard
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.bold
import com.team6.smartbudget.core.presentation.util.shimmer
import com.team6.smartbudget.sma1.R

@Composable
fun TrackCard(
    track: TrackSummaryEntity?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TCard(onClick = onClick, modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(TTheme.spacing.xs)) {
            NetworkImage(
                { data(track?.thumbnailUrl) },
                contentDescription = null,
                Modifier
                    .shimmer(track == null, TTheme.shape.m)
                    .aspectRatio(1f)
                    .fillMaxWidth()
                    .clip(TTheme.shape.l),
            )
            Text(
                track?.title ?: stringResource(R.string.placeholder_short),
                Modifier.shimmer(track == null, TTheme.shape.m),
                style = TTheme.typography.bodyXL.bold(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                track?.artist ?: stringResource(R.string.placeholder_short),
                Modifier.shimmer(track == null, TTheme.shape.m),
                color = TTheme.colorScheme.onBackgroundLow,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun TrackCardPreview() {
    TPreviewTheme {
        TrackCard(
            TrackSummaryEntity(
                title = stringResource(R.string.label_title),
                artist = stringResource(R.string.label_artist),
                thumbnailUrl = "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
            ),
            onClick = {},
        )
    }
}

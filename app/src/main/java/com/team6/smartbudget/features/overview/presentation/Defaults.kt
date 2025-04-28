package com.team6.smartbudget.features.overview.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.team6.smartbudget.core.domain.TrackSummaryEntity
import com.team6.smartbudget.sma1.R

private const val DefaultThumbnailUrl =
    "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png"

@Composable
fun defaultTracks(): List<TrackSummaryEntity> {
    return listOf(
        TrackSummaryEntity(
            title = stringResource(R.string.label_title),
            artist = stringResource(R.string.label_artist),
            thumbnailUrl = DefaultThumbnailUrl,
        ),
        TrackSummaryEntity(
            title = stringResource(R.string.label_title),
            artist = stringResource(R.string.label_artist),
            thumbnailUrl = DefaultThumbnailUrl,
        ),
    )
}

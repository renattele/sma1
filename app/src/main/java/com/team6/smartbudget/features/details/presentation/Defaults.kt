package com.team6.smartbudget.features.details.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.team6.smartbudget.core.domain.DataSourceType
import com.team6.smartbudget.features.details.domain.TrackDetailsEntity
import com.team6.smartbudget.sma1.R

private const val DefaultThumbnailUrl =
    "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png"

@Composable
fun defaultTrackDetails(): TrackDetailsEntity = TrackDetailsEntity(
    title = stringResource(R.string.label_title),
    artist = stringResource(R.string.label_artist),
    thumbnailUrl = DefaultThumbnailUrl,
    summary = stringResource(R.string.label_summary),
    published = "27 January 2222",
    sourceType = DataSourceType.Remote
)

package com.team6.smartbudget.features.details.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import com.team6.smartbudget.core.domain.DataSourceType
import com.team6.smartbudget.core.presentation.designsystem.components.NetworkImage
import com.team6.smartbudget.core.presentation.designsystem.components.TErrorSheet
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.bold
import com.team6.smartbudget.core.presentation.util.shimmer
import com.team6.smartbudget.core.presentation.viewmodel.providedViewModel
import com.team6.smartbudget.features.details.presentation.DetailsScreenState.FailureReason
import com.team6.smartbudget.sma1.R

@Composable
fun DetailsScreen(
    artist: String,
    title: String,
    onGoBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = providedViewModel<DetailsViewModel.Dependencies, DetailsViewModel> {
        DetailsViewModel.Dependencies(artist, title, onGoBack)
    }
    val state = viewModel.state.collectAsState()
    DetailsScreen(state.value, viewModel::handleEvent, modifier)
}

@Composable
fun DetailsScreen(
    state: DetailsScreenState,
    onEvent: (DetailsScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .background(TTheme.colorScheme.background)
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(TTheme.spacing.l),
    ) {
        when (state) {
            is DetailsScreenState.Failure -> DetailsScreenFailure(state, onEvent)
            else -> DetailsScreenUniversal(
                state = state as? DetailsScreenState.Success,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreenFailure(
    state: DetailsScreenState.Failure,
    onEvent: (DetailsScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    TErrorSheet(onDismissRequest = {
        onEvent(DetailsScreenEvent.GoBack)
    }, modifier) {
        val reasonText = when (state.reason) {
            FailureReason.InvalidApiKey -> stringResource(R.string.reason_invalid_api_key)
            FailureReason.NoInternet -> stringResource(R.string.reason_no_internet)
            FailureReason.TrackNotFound -> stringResource(R.string.reason_track_not_found)
        }
        Text(reasonText)
    }
}

@Composable
private fun DetailsScreenUniversal(
    state: DetailsScreenState.Success?,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxSize()) {
        NetworkImage(
            { data(state?.track?.thumbnailUrl) },
            contentDescription = null,
            Modifier
                .padding(bottom = TTheme.spacing.l)
                .shimmer(state == null)
                .clip(CircleShape)
                .aspectRatio(1f)
                .fillMaxWidth(),
        )
        Text(
            stringResource(
                when (state?.track?.sourceType) {
                    DataSourceType.Remote -> R.string.label_latest_result
                    DataSourceType.Local -> R.string.label_from_cache
                    null -> R.string.placeholder_short
                }
            ),
            Modifier.shimmer(state == null, shape = TTheme.shape.m),
            style = TTheme.typography.bodyS,
            color = TTheme.colorScheme.onBackgroundLow
        )
        Text(
            state?.track?.title ?: stringResource(R.string.placeholder_short),
            Modifier.shimmer(state == null, shape = TTheme.shape.m),
            style = TTheme.typography.heading2,
        )
        Text(
            state?.track?.artist ?: stringResource(R.string.placeholder_short),
            Modifier
                .padding(bottom = TTheme.spacing.m)
                .shimmer(state == null, shape = TTheme.shape.m),
            style = TTheme.typography.bodyXL,
            color = TTheme.colorScheme.onBackgroundLow,
        )
        Text(stringResource(R.string.label_summary), style = TTheme.typography.bodyL.bold())
        Text(
            state?.track?.summary ?: stringResource(R.string.placeholder_long),
            Modifier.shimmer(state == null, shape = TTheme.shape.m),
            style = TTheme.typography.bodyM,
        )
    }
}

class DetailsScreenStateProvider :
    CollectionPreviewParameterProvider<@Composable () -> DetailsScreenState>(
        listOf(
            { DetailsScreenState.Loading },
            { DetailsScreenState.Success(defaultTrackDetails()) },
            { DetailsScreenState.Failure(FailureReason.TrackNotFound) },
        ),
    )

@Preview
@Composable
private fun DetailsScreenPreview(
    @PreviewParameter(DetailsScreenStateProvider::class) state:
    @Composable () -> DetailsScreenState,
) {
    TTheme {
        DetailsScreen(state(), onEvent = {})
    }
}

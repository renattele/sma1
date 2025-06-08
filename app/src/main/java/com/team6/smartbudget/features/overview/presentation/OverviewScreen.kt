package com.team6.smartbudget.features.overview.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.team6.smartbudget.core.domain.TrackSummaryEntity
import com.team6.smartbudget.core.presentation.designsystem.components.TButton
import com.team6.smartbudget.core.presentation.designsystem.components.TButtonStyle
import com.team6.smartbudget.core.presentation.designsystem.components.TErrorSheet
import com.team6.smartbudget.core.presentation.designsystem.components.TScaffold
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.util.plus
import com.team6.smartbudget.core.presentation.viewmodel.providedViewModel
import com.team6.smartbudget.core.util.listOfNulls
import com.team6.smartbudget.features.overview.presentation.OverviewScreenState.FailureReason
import com.team6.smartbudget.features.overview.presentation.components.TrackCard
import com.team6.smartbudget.sma1.R

private val MinCardWidth = 150.dp

private const val DefaultPlaceholderTracksCount = 8

@Composable
fun OverviewScreen(
    onGoToTrack: (TrackSummaryEntity) -> Unit,
    onGoBack: () -> Unit,
    onGoToGraph: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = providedViewModel<OverviewViewModel.Dependencies, OverviewViewModel> {
        OverviewViewModel.Dependencies(onGoToTrack, onGoBack, onGoToGraph)
    }
    val state = viewModel.state.collectAsState()
    OverviewScreen(state.value, onEvent = viewModel::handleEvent, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    state: OverviewScreenState,
    onEvent: (OverviewScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    TScaffold(modifier, title = {
        Text(stringResource(R.string.label_app_name))
    }, backButton = {
        TButton(onClick = {
            onEvent(OverviewScreenEvent.GoToGraph)
        }, style = TButtonStyle.flat()) {
            Icon(Icons.Outlined.LocationOn, contentDescription = null)
        }
    }) { padding ->
        when (state) {
            is OverviewScreenState.Failure -> OverviewScreenFailure(state, onEvent)

            else -> TrackListUniversal(state as? OverviewScreenState.Success, onEvent, padding)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreenFailure(
    state: OverviewScreenState.Failure,
    onEvent: (OverviewScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    TErrorSheet(onDismissRequest = {
        onEvent(OverviewScreenEvent.GoBack)
    }, modifier) {
        val reasonText = when (state.reason) {
            FailureReason.InvalidApiKey -> stringResource(R.string.reason_invalid_api_key)
            FailureReason.NoInternet -> stringResource(R.string.reason_no_internet)
        }
        Text(reasonText)
    }
}

@Composable
private fun TrackListUniversal(
    state: OverviewScreenState.Success?,
    onEvent: (OverviewScreenEvent) -> Unit,
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        GridCells.Adaptive(MinCardWidth),
        modifier,
        contentPadding = padding + PaddingValues(horizontal = TTheme.spacing.m),
        horizontalArrangement = Arrangement.spacedBy(TTheme.spacing.s),
        verticalArrangement = Arrangement.spacedBy(TTheme.spacing.s),
    ) {
        items(
            state?.tracks ?: listOfNulls<TrackSummaryEntity>(DefaultPlaceholderTracksCount),
        ) { track ->
            TrackCard(track, {
                if (track != null) onEvent(OverviewScreenEvent.GoToTrack(track))
            }, Modifier.padding(TTheme.spacing.xs))
        }
    }
}

private class OverviewScreenStateProvider :
    CollectionPreviewParameterProvider<@Composable () -> OverviewScreenState>(
        listOf(
            { OverviewScreenState.Loading },
            { OverviewScreenState.Failure(OverviewScreenState.FailureReason.InvalidApiKey) },
            { OverviewScreenState.Success(defaultTracks()) },
        ),
    )

@Preview
@Composable
private fun OverviewScreenPreview(
    @PreviewParameter(OverviewScreenStateProvider::class) state:
        @Composable () -> OverviewScreenState,
) {
    TTheme {
        OverviewScreen(
            state = state(),
            onEvent = {},
        )
    }
}

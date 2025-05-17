package com.team6.smartbudget.features.graph.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import com.team6.smartbudget.core.presentation.designsystem.components.TButton
import com.team6.smartbudget.core.presentation.designsystem.components.TScaffold
import com.team6.smartbudget.core.presentation.designsystem.components.TTextField
import com.team6.smartbudget.core.presentation.designsystem.components.TTextFieldAction
import com.team6.smartbudget.core.presentation.designsystem.components.Text
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.util.FullLineColumn
import com.team6.smartbudget.core.presentation.viewmodel.daggerViewModel
import com.team6.smartbudget.features.graph.presentation.GraphScreenState.FailureReason
import com.team6.smartbudget.features.graph.presentation.components.Graph
import com.team6.smartbudget.sma1.R

@Composable
fun GraphScreen(modifier: Modifier = Modifier) {
    val viewModel = daggerViewModel<GraphViewModel>()
    val state = viewModel.state.collectAsState()
    GraphScreen(state.value, viewModel::handleEvent, modifier)
}

@Composable
fun GraphScreen(
    state: GraphScreenState,
    onEvent: (GraphScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    TScaffold(modifier, title = {
        Text(stringResource(R.string.label_graph))
    }) { padding ->
        FullLineColumn(
            Modifier
                .padding(TTheme.spacing.l)
                .padding(padding),
            spacing = TTheme.spacing.m
        ) {
            var pointsCountStr by remember { mutableStateOf(state.pointsCount?.toString() ?: "") }
            var pointsStr by remember { mutableStateOf(state.points?.joinToString(",") ?: "") }

            val showPointsCountError =
                state.failureReason == FailureReason.InvalidPointsCount
            val showPointsError =
                state.failureReason == FailureReason.InvalidPoints ||
                    state.failureReason == FailureReason.InvalidActualPointsCount

            TTextField(
                TTextFieldAction.Text(pointsCountStr) { newValue ->
                    pointsCountStr = newValue
                    onEvent(GraphScreenEvent.EditPointsCount(newValue))
                }, label = {
                    Text(stringResource(R.string.label_count_of_points))
                }, showError = showPointsCountError, supportingText = if (showPointsCountError) {
                    {
                        Text(stringResource(R.string.label_count_of_points_should_be_valid))
                    }
                } else null)

            TTextField(
                TTextFieldAction.Text(pointsStr) { newValue ->
                    pointsStr = newValue
                    onEvent(GraphScreenEvent.EditPoints(newValue))
                }, label = {
                    Text(stringResource(R.string.label_points))
                }, showError = showPointsError, supportingText = if (showPointsError) {
                    {
                        val failureStringRes = when (state.failureReason) {
                            FailureReason.InvalidActualPointsCount ->
                                R.string.label_actual_point_count_show_me_same

                            FailureReason.InvalidPoints ->
                                R.string.label_points_should_be_valid

                            else -> null
                        }
                        if (failureStringRes != null) {
                            Text(stringResource(failureStringRes))
                        }
                    }
                } else null)

            TButton(onClick = {
                onEvent(GraphScreenEvent.Render)
            }) {
                Text(stringResource(R.string.label_render))
            }
            AnimatedVisibility(visible = state.shouldRender && state.failureReason == null) {
                if (state.points != null) {
                    Graph(state.points)
                }
            }
        }
    }
}

private class GraphScreenStateProvider : CollectionPreviewParameterProvider<GraphScreenState>(
    listOf(
        GraphScreenState(),
        GraphScreenState(failureReason = FailureReason.InvalidPointsCount),
        GraphScreenState(failureReason = FailureReason.InvalidPoints),
        GraphScreenState(failureReason = FailureReason.InvalidActualPointsCount),
    )
)

@PreviewLightDark
@Composable
private fun GraphScreenPreview(@PreviewParameter(GraphScreenStateProvider::class) state: GraphScreenState) {
    TPreviewTheme(enablePadding = false) {
        GraphScreen(state, onEvent = {})
    }
}
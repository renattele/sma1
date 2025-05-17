package com.team6.smartbudget.features.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import com.team6.smartbudget.core.presentation.designsystem.components.TButton
import com.team6.smartbudget.core.presentation.designsystem.components.TErrorSheet
import com.team6.smartbudget.core.presentation.designsystem.components.TScaffold
import com.team6.smartbudget.core.presentation.designsystem.components.TTextField
import com.team6.smartbudget.core.presentation.designsystem.components.TTextFieldAction
import com.team6.smartbudget.core.presentation.designsystem.components.Text
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.viewmodel.providedViewModel

@Composable
fun OnboardingScreen(onNext: () -> Unit, modifier: Modifier = Modifier) {
    val viewModel =
        providedViewModel<OnboardingViewModel.Dependencies, OnboardingViewModel> {
            OnboardingViewModel.Dependencies(onNext)
        }
    val state = viewModel.state.collectAsState()
    OnboardingScreen(state.value, viewModel::handleEvent, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    state: OnboardingScreenState,
    onEvent: (OnboardingScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.failureReason != null) {
        TErrorSheet(onDismissRequest = {
            onEvent(OnboardingScreenEvent.DismissError)
        }) {
            when (state.failureReason) {
                OnboardingScreenState.FailureReason.InvalidApiKey -> Text(
                    stringResource(com.team6.smartbudget.core.ui.R.string.reason_invalid_api_key)
                )

                OnboardingScreenState.FailureReason.NetworkError -> Text(
                    stringResource(com.team6.smartbudget.core.ui.R.string.reason_no_internet)
                )

                else -> {}
            }
        }
    }
    TScaffold(modifier) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(TTheme.spacing.l)
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(TTheme.spacing.m, Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(R.string.onboarding_title),
                style = TTheme.typography.heading1,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.onboarding_subtitle),
                style = TTheme.typography.bodyXL,
                textAlign = TextAlign.Center
            )
            // Need this state because TextField is buggy with StateFlows
            val apiKey = remember(state.apiKey) { mutableStateOf(state.apiKey) }
            TTextField(TTextFieldAction.Text(apiKey.value) {
                apiKey.value = it
                onEvent(OnboardingScreenEvent.EditApiKey(it))
            }, Modifier.fillMaxWidth(), label = {
                Text(stringResource(R.string.onboarding_api_key))
            })
            TButton(onClick = {
                onEvent(OnboardingScreenEvent.Next)
            }) {
                Icon(Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = null)
                Text(stringResource(R.string.onboarding_next))
            }
        }
    }
}

private class OnboardingScreenStateProvider :
    CollectionPreviewParameterProvider<@Composable () -> OnboardingScreenState>(
        listOf(
            { OnboardingScreenState() },
            {
                OnboardingScreenState(
                    failureReason =
                        OnboardingScreenState.FailureReason.InvalidApiKey
                )
            }
        )
    )

@Preview
@Composable
private fun OnboardingScreenPreview(
    @PreviewParameter(OnboardingScreenStateProvider::class)
    state: @Composable () -> OnboardingScreenState
) {
    TPreviewTheme(enablePadding = false) {
        OnboardingScreen(state(), onEvent = {})
    }
}
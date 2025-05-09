package com.team6.smartbudget.features.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme

@Composable
fun OnboardingScreen(onNext: () -> Unit, modifier: Modifier = Modifier) {

}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    TPreviewTheme(enablePadding = false) {
        OnboardingScreen()
    }
}
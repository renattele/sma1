package com.renattele.sma1.presentation.screen.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.renattele.sma1.R
import com.renattele.sma1.presentation.theme.AppTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SplashScreen(state: SplashScreenState, modifier: Modifier = Modifier) {
    Scaffold(modifier) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
        ) {
            CircularWavyProgressIndicator(
                Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f))
            AnimatedVisibility(state.username != null) {
                Text(
                    stringResource(R.string.hello, state.username ?: ""),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    AppTheme {
        SplashScreen(
            SplashScreenState(
                username = "User",
                error = null
            )
        )
    }
}
package com.renattele.sma1.presentation.components

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun ErrorSnackbarHost(text: String?, modifier: Modifier = Modifier) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(text) {
        if (text != null) {
            snackbarHostState.showSnackbar(text)
        }
    }
    SnackbarHost(snackbarHostState, modifier)
}
package com.renattele.sma1.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.delay

typealias ErrorState<T> = MutableState<T?>

@Composable
fun ErrorState<Int>.asText(): String? {
    return value?.let { stringResource(it) }
}

@Composable
fun <T> rememberErrorState(delay: Long = 1000): ErrorState<T> {
    val v = remember { mutableStateOf<T?>(null) }
    LaunchedEffect(v) {
        delay(delay)
        v.value = null
    }
    return v
}
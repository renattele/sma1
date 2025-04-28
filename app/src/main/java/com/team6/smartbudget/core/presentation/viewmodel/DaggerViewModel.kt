package com.team6.smartbudget.core.presentation.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

val LocalViewModelFactory =
    staticCompositionLocalOf<ViewModelProvider.Factory> {
        error("LocalViewModelFactory has not been initialized!")
    }

@Composable
inline fun <reified T : ViewModel> daggerViewModel(): T {
    val factory = LocalViewModelFactory.current
    return viewModel(
        factory = factory,
    )
}

@Composable
inline fun <D, reified T : ProvidedViewModel<D>> providedViewModel(
    noinline dependencies: @DisallowComposableCalls () -> D,
): T {
    val factory = LocalViewModelFactory.current
    val viewModel = viewModel<T>(
        factory = factory,
    )
    // Needed to use remember because it's needed to include dependencies in first composition
    remember { viewModel.initializeSafely(dependencies) }
    return viewModel
}

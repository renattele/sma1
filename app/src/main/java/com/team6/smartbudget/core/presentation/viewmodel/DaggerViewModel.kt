package com.team6.smartbudget.core.presentation.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.team6.smartbudget.component

@Composable
inline fun <reified T : ViewModel> daggerViewModel(): T {
    val context = LocalContext.current
    val component = context.component
    val factory = component.viewModelFactory
    return viewModel(
        factory = factory,
    )
}

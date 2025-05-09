package com.team6.smartbudget.core.presentation.util

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider

class BooleanPreviewParameterProvider :
    CollectionPreviewParameterProvider<Boolean>(
        listOf(
            true,
            false,
        ),
    )

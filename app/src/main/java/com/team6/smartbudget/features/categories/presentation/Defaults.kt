package com.team6.smartbudget.features.categories.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import com.team6.smartbudget.sma1.R
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import kotlin.uuid.Uuid

@Composable
fun defaultCategories(): List<UiCategoryEntity> = listOf(
    UiCategoryEntity(
        id = Uuid.random(),
        name = stringResource(R.string.label_very_long),
        targetPercent = 0.2f,
        icon = "icon_1",
        color = TTheme.colorScheme.primary.toArgb(),
        enabled = true,
        transactionCount = 1,
        spentPercent = 0.1f,
        spentAmount = 1f,
        targetAmount = 100f,
        spentRelativePercent = 0.5f,
    ),
    UiCategoryEntity(
        id = Uuid.random(),
        name = stringResource(R.string.label_categories),
        targetPercent = 0.5f,
        icon = "icon_2",
        color = TTheme.colorScheme.success.toArgb(),
        enabled = true,
        transactionCount = 1,
        spentPercent = 0.5f,
        spentAmount = 1f,
        targetAmount = 100f,
        spentRelativePercent = 1f,
    ),
    UiCategoryEntity(
        id = Uuid.random(),
        name = stringResource(R.string.label_categories),
        targetPercent = 0.5f,
        icon = "icon_3",
        color = TTheme.colorScheme.warning.toArgb(),
        enabled = false,
        transactionCount = 1,
        spentPercent = 0.5f,
        spentAmount = 1f,
        targetAmount = 100f,
        spentRelativePercent = 0f,
    ),
)

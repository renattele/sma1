package com.team6.smartbudget.core.presentation.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

operator fun PaddingValues.plus(other: PaddingValues) = object : PaddingValues {
    override fun calculateTopPadding() =
        this@plus.calculateTopPadding() + other.calculateTopPadding()

    override fun calculateBottomPadding() =
        this@plus.calculateBottomPadding() + other.calculateBottomPadding()

    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp =
        this@plus.calculateLeftPadding(layoutDirection) +
            other.calculateLeftPadding(layoutDirection)

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp =
        this@plus.calculateRightPadding(layoutDirection) +
            other.calculateRightPadding(layoutDirection)
}

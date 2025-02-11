package com.renattele.sma1.presentation.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

operator fun PaddingValues.plus(other: PaddingValues) = object : PaddingValues {
    override fun calculateBottomPadding(): Dp {
        return this@plus.calculateBottomPadding() + other.calculateBottomPadding()
    }

    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp {
        return this@plus.calculateLeftPadding(layoutDirection) + other.calculateLeftPadding(
            layoutDirection
        )
    }

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp {
        return this@plus.calculateRightPadding(layoutDirection) + other.calculateRightPadding(
            layoutDirection
        )
    }

    override fun calculateTopPadding(): Dp {
        return this@plus.calculateTopPadding() + other.calculateTopPadding()
    }

}
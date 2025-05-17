package com.team6.smartbudget.core.presentation.designsystem.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

data class TShape(
    val xs: CornerBasedShape,
    val s: CornerBasedShape,
    val m: CornerBasedShape,
    val l: CornerBasedShape,
    val xl: CornerBasedShape,
)

fun defaultTShape() = TShape(
    xs = RoundedCornerShape(4.dp),
    s = RoundedCornerShape(8.dp),
    m = RoundedCornerShape(12.dp),
    l = RoundedCornerShape(16.dp),
    xl = RoundedCornerShape(24.dp),
)

val LocalTShape = staticCompositionLocalOf { defaultTShape() }

package com.team6.smartbudget.core.presentation.util

import androidx.compose.ui.Modifier

inline fun Modifier.runWhen(
    condition: Boolean,
    block: Modifier.() -> Modifier,
): Modifier = this.then(if (condition) block() else Modifier)

inline fun <T> Modifier.runWhenNotNull(
    value: T?,
    block: Modifier.(T) -> Modifier,
): Modifier = this.then(if (value != null) Modifier.block(value) else Modifier)

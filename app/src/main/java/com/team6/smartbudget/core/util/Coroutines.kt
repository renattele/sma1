package com.team6.smartbudget.core.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

/**
 * Converts a [Flow] to a [SharedFlow]. Difference between
 * [shareIn] and [toSharedFlow] is that [toSharedFlow] has [replayCount] = 1 by default
 * and [sharingStarted] is [SharingStarted.Lazily]
 *
 * @param coroutineScope the [CoroutineScope] to run the flow in.
 * @param sharingStarted the [SharingStarted] strategy.
 * @param replayCount the number of elements to replay.
 * @return a [SharedFlow] that shares the flow with the given [coroutineScope].
 */
fun <T> Flow<T>.toSharedFlow(
    coroutineScope: CoroutineScope,
    sharingStarted: SharingStarted = SharingStarted.Lazily,
    replayCount: Int = 1,
): SharedFlow<T> = shareIn(coroutineScope, sharingStarted, replayCount)

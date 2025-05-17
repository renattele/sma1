package com.team6.smartbudget.core.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CompletableDeferred

abstract class ProvidedViewModel<D> : ViewModel() {
    private val dependencies = CompletableDeferred<D>()

    /**
     * Waits for dependencies and returns them.
     *
     * **NOTE:** if dependencies are not initialized,
     * this function will be waiting indefinitely
     */
    suspend fun dependencies(): D {
        if (!dependencies.isCompleted) {
            Log.w(
                this::class.qualifiedName,
                "Waiting for dependencies initialization for $this! " +
                    "Make sure that you properly initialized this ViewModel!",
            )
        }
        return dependencies.await()
    }

    @PublishedApi
    internal fun initializeSafely(dependencies: () -> D) {
        if (!this.dependencies.isCompleted) {
            this.dependencies.complete(dependencies())
        }
    }
}

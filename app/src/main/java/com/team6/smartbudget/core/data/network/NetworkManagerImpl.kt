package com.team6.smartbudget.core.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import com.team6.smartbudget.core.domain.network.NetworkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class NetworkManagerImpl @Inject constructor(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
) : NetworkManager {
    private val connectivityManager = context.getSystemService<ConnectivityManager>()
    private val isOnline = flow {
        if (connectivityManager == null) return@flow
        while (true) {
            emit(connectivityManager.isOnline())
            delay(1.seconds)
        }
    }.stateIn(
        coroutineScope,
        SharingStarted.Eagerly,
        connectivityManager?.isOnline() == true,
    )

    private fun ConnectivityManager.isOnline(): Boolean {
        val activeNetwork = activeNetwork
        val capabilities = getNetworkCapabilities(activeNetwork)
        return capabilities != null &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    override fun isOnline(): StateFlow<Boolean> = isOnline
}

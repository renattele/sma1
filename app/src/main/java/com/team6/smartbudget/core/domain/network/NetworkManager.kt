package com.team6.smartbudget.core.domain.network

import kotlinx.coroutines.flow.StateFlow

interface NetworkManager {
    fun isOnline(): StateFlow<Boolean>
}

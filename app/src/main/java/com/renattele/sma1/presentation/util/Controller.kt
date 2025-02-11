package com.renattele.sma1.presentation.util

import androidx.navigation.NavController

fun NavController.navigateWithPopBackStack(destination: String) {
    val current = currentDestination?.route
    navigate(destination) {
        if (current != null) {
            popUpTo(current) { inclusive = true }
        }
    }
}
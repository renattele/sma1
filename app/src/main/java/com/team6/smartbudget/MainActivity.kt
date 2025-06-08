package com.team6.smartbudget

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.team6.smartbudget.core.presentation.navigation.rememberBottomSheetNavigator
import com.team6.smartbudget.shared.presentation.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    companion object {
        var isAppInForeground = false
            private set
        var destination: NavDestination? = null
            private set

        var navigate: (Any) -> Unit = {}
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val controller = rememberNavController(bottomSheetNavigator)
            val backStackEntry = controller.currentBackStackEntryAsState().value
            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(backStackEntry) {
                destination = backStackEntry?.destination
                navigate = {
                    coroutineScope.launch(Dispatchers.Main) {
                        controller.navigate(it)
                    }
                }
            }
            App(
                bottomSheetNavigator = bottomSheetNavigator,
                controller = controller
            )
        }
        requestNotificationPermission()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        }
    }

    override fun onStart() {
        super.onStart()
        isAppInForeground = true
    }

    override fun onStop() {
        super.onStop()
        isAppInForeground = false
    }
}

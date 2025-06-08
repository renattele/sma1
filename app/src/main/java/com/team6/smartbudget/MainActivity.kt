package com.team6.smartbudget

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.team6.smartbudget.core.presentation.navigation.rememberBottomSheetNavigator
import com.team6.smartbudget.shared.domain.AppConfig
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

        const val FEATURE_DETAILS_ENABLED_KEY = "feature_detail_enabled"
        var appConfig by mutableStateOf(AppConfig())
            private set
    }

    private var remoteConfig: FirebaseRemoteConfig? = null
    private var crashlytics: FirebaseCrashlytics? = null

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
                config = appConfig,
                bottomSheetNavigator = bottomSheetNavigator,
                controller = controller,
            )
        }
        requestNotificationPermission()
        setupFirebase()
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

    private fun setupFirebase() {
        crashlytics = FirebaseCrashlytics.getInstance()

        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getString("user_id", "anonymous_user") ?: "anonymous_user"
        crashlytics?.setCustomKey("user_id", userId)
        crashlytics?.setCustomKey("screen_name", "MainActivity")

        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        val defaults = mapOf(FEATURE_DETAILS_ENABLED_KEY to true)
        remoteConfig.setDefaultsAsync(defaults)

        fetchRemoteConfig()
    }

    private fun fetchRemoteConfig() {
        remoteConfig?.fetchAndActivate()
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    appConfig = AppConfig(
                        detailsEnabled = remoteConfig?.getBoolean(FEATURE_DETAILS_ENABLED_KEY)
                            ?: false
                    )
                    crashlytics?.setCustomKey("remote_config_fetch", "success")
                } else {
                    crashlytics?.setCustomKey("remote_config_fetch", "failed")
                }
            }
    }
}

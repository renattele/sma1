package com.team6.smartbudget

import android.Manifest
import android.content.Context
import android.graphics.Color
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
import com.team6.smartbudget.features.chart.presentation.ChartSector
import com.team6.smartbudget.features.chart.presentation.CircularChartView
import com.team6.smartbudget.shared.domain.AppConfig
import com.team6.smartbudget.shared.presentation.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.graphics.toColorInt
import com.team6.smartbudget.features.chart.presentation.ChartData
import com.team6.smartbudget.sma1.R

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
        val chartView = CircularChartView(this)

        val sectors = listOf(
            ChartSector(
                52f,
                "#8BC34A".toColorInt(),
                iconRes = R.drawable.ic_outline_fastfood_24
            ),
            ChartSector(
                60f,
                "#00BCD4".toColorInt(),
                iconRes = R.drawable.ic_outline_warning_24
            ),
            ChartSector(
                70f,
                "#607D8B".toColorInt(),
                iconRes = R.drawable.ic_outline_fastfood_24
            ),
            ChartSector(
                80f,
                "#2196F3".toColorInt(),
                iconRes = R.drawable.ic_outline_fastfood_24
            )
        )
        val data = ChartData(
            icon = R.drawable.ic_outline_warning_24,
            title = getString(R.string.label_title),
            subtitle = getString(R.string.label_subtitle),
            sectors = sectors
        )

        chartView.setData(data)
        setContentView(chartView)
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

        remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig?.setConfigSettingsAsync(configSettings)

        val defaults = mapOf(FEATURE_DETAILS_ENABLED_KEY to true)
        remoteConfig?.setDefaultsAsync(defaults)

        fetchRemoteConfig()
    }

    private fun fetchRemoteConfig() {
        println("AAAAAAfffff")
        remoteConfig?.fetchAndActivate()
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    appConfig = AppConfig(
                        detailsEnabled = remoteConfig?.getBoolean(FEATURE_DETAILS_ENABLED_KEY)
                            ?: false
                    )
                    println(remoteConfig?.getBoolean(FEATURE_DETAILS_ENABLED_KEY))
                    println(appConfig)
                    crashlytics?.setCustomKey("remote_config_fetch", "success")
                } else {
                    crashlytics?.setCustomKey("remote_config_fetch", "failed")
                }
            }
    }
}

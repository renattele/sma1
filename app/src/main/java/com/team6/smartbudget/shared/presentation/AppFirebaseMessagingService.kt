package com.team6.smartbudget.shared.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDestination.Companion.hasRoute
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.team6.smartbudget.MainActivity
import com.team6.smartbudget.component
import com.team6.smartbudget.core.domain.usecase.GetApiKeyUseCase
import com.team6.smartbudget.sma1.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var getApiKeyUseCase: GetApiKeyUseCase

    companion object {
        const val TAG = "FCMService"
        const val CHANNEL_ID = "firebase_notifications"
        const val CATEGORY_NOTIFICATION = "notification"
        const val CATEGORY_SILENT = "silent"
        const val CATEGORY_NAVIGATION = "navigation"
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")

        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.log("FCM message received: ${remoteMessage.data}")

        runBlocking(Dispatchers.IO) {
            if (remoteMessage.data.isNotEmpty()) {
                Log.d(TAG, "Message data payload: ${remoteMessage.data}")

                when (val category = remoteMessage.data["category"]) {
                    CATEGORY_NOTIFICATION -> handleNotificationCategory(remoteMessage.data)
                    CATEGORY_SILENT -> handleSilentCategory(remoteMessage.data)
                    CATEGORY_NAVIGATION -> handleNavigationCategory(remoteMessage.data)
                    else -> Log.w(TAG, "Unknown category: $category")
                }
            }
        }
    }

    private fun handleNotificationCategory(data: Map<String, String>) {
        val title = data["title"] ?: getString(R.string.label_notification_title)
        val message = data["message"] ?: getString(R.string.label_notification_message)

        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)
        try {
            notificationManager.notify(System.currentTimeMillis().toInt(), notification)
            Log.d(TAG, "Notification shown: $title")
        } catch (e: SecurityException) {
            Log.e(TAG, "Failed to show notification", e)
        }
    }

    private fun handleSilentCategory(data: Map<String, String>) {
        val category = data["category"] ?: ""
        val extraData = data["extra_data"] ?: ""

        val prefs = getSharedPreferences("silent_notifications", Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString("last_category", category)
            putString("last_data", extraData)
            putLong("timestamp", System.currentTimeMillis())
            apply()
        }

        Log.d(TAG, "Silent notification saved: category=$category, data=$extraData")

        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCustomKey("last_silent_notification", "$category:$extraData")
    }

    private suspend fun handleNavigationCategory(data: Map<String, String>) {
        val targetScreen = data["target_screen"] ?: ""
        val requiresAuth = data["requires_auth"]?.toBoolean() ?: false

        if (!MainActivity.isAppInForeground) {
            Log.d(TAG, "App not in foreground, ignoring navigation message")
            return
        }

        when (targetScreen) {
            "detail" -> {
                if (!MainActivity.appConfig.detailsEnabled) {
                    return
                } else if (MainActivity.destination?.hasRoute<Destination.TrackDetails>() == true) {
                    showToast(getString(R.string.label_already_on_screen))
                } else if (requiresAuth && !isUserAuthenticated()) {
                    showToast(getString(R.string.label_requires_auth))
                } else {
                    MainActivity.navigate(
                        Destination.TrackDetails(
                            artist = data["artist"] ?: return,
                            title = data["title"] ?: return
                        )
                    )
                }
            }

            else -> {
                Log.w(TAG, "Unknown target screen: $targetScreen")
            }
        }
    }

    private suspend fun isUserAuthenticated(): Boolean {
        return getApiKeyUseCase().first() != null
    }

    private suspend fun showToast(message: String) {
        withContext(Dispatchers.Main) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(this@AppFirebaseMessagingService, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.label_notification_channel)
        val descriptionText = getString(R.string.label_notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCustomKey("fcm_token_refreshed", token)

        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        Log.d(TAG, "Sending token to server: $token")
    }
}
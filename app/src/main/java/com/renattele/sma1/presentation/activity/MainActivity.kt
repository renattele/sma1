package com.renattele.sma1.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.renattele.sma1.R
import com.renattele.sma1.app
import com.renattele.sma1.databinding.ActivityMainBinding
import com.renattele.sma1.presentation.fragment.NotificationFragment
import com.renattele.sma1.utils.NavigationAction
import com.renattele.sma1.utils.ScreenTags
import com.renattele.sma1.utils.showToast

class MainActivity : BaseActivity() {
    private var binding: ActivityMainBinding? = null
    override val mainContainerId: Int = R.id.fragment_container_view

    companion object {
        const val FROM_NOTIFICATION_KEY = "FROM_NOTIFICATION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app.theme?.let { theme ->
            setTheme(theme.themeResource)
        }
        if (intent.getBooleanExtra(FROM_NOTIFICATION_KEY, false)) {
            showToast(getString(R.string.launched_from_notification))
        }
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        if (savedInstanceState == null) {
            navigate(
                destination = NotificationFragment(),
                destinationTag = ScreenTags.NOTIFICATION_TAG,
                action = NavigationAction.ADD,
                isAddToBackStack = false
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
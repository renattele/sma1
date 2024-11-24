package com.renattele.sma1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.renattele.sma1.databinding.ActivityMainBinding
import com.renattele.sma1.utils.NavigationAction
import com.renattele.sma1.utils.ScreenTags

class MainActivity : BaseActivity() {
    private var binding: ActivityMainBinding? = null
    override val mainContainerId: Int = R.id.fragment_container_view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        if (savedInstanceState == null) {
            navigate(
                destination = SurveyFragment(),
                destinationTag = ScreenTags.SURVEY_TAG,
                action = NavigationAction.ADD
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
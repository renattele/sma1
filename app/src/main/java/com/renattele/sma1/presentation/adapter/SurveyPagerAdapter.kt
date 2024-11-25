package com.renattele.sma1.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.renattele.sma1.presentation.fragment.SurveyQuestionFragment

class SurveyPagerAdapter(
    private val size: Int,
    manager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(manager, lifecycle) {
    override fun getItemCount(): Int = size
    override fun createFragment(position: Int): Fragment {
        return SurveyQuestionFragment.newInstance(position)
    }
}
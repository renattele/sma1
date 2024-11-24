package com.renattele.sma1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.renattele.sma1.data.SurveysRepository
import com.renattele.sma1.databinding.FragmentSurveyBinding
import com.renattele.sma1.domain.SurveyQuestion
import com.renattele.sma1.presentation.adapter.SurveyPagerAdapter

class SurveyFragment : Fragment() {
    private var _binding: FragmentSurveyBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        SurveyPagerAdapter(onAnswer = ::onAnswer, SurveysRepository.questions)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSurveyBinding.inflate(inflater)
        with(binding) {
            pager.adapter = adapter
            pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    updatePageInfo(position)
                }
            })
            previousButton.setOnClickListener {
                if (pager.currentItem > 0) {
                    pager.currentItem -= 1
                }
            }
            nextButton.setOnClickListener {
                if (pager.currentItem == adapter.itemCount - 1) {
                    Snackbar
                        .make(
                            pager,
                            getString(R.string.answers_saved),
                            Snackbar.LENGTH_SHORT
                        )
                        .setAnchorView(previousButton)
                        .show()
                } else {
                    pager.currentItem += 1
                }
            }
        }
        return binding.root
    }

    private fun updatePageInfo(position: Int = binding.pager.currentItem) {
        with(binding) {
            pageCount.text = getString(R.string.page_count, position + 1, adapter.itemCount)
            previousButton.isEnabled = position > 0
            val isLastPosition = position == adapter.itemCount - 1
            val allChecked = SurveysRepository.questions.all { it.selectedAnswer != null }
            nextButton.isEnabled = allChecked || !isLastPosition
            if (isLastPosition) {
                nextButton.text = getString(R.string.finish)
            } else {
                nextButton.text = getString(R.string.next)
            }
        }
    }

    private fun onAnswer(question: SurveyQuestion, answer: Int) {
        SurveysRepository.update {
            val index = indexOfFirst { it.id == question.id }
            if (index == -1) return@update
            this[index] = this[index].copy(selectedAnswer = answer)
        }
        adapter.update(SurveysRepository.questions)
        updatePageInfo()
    }
}
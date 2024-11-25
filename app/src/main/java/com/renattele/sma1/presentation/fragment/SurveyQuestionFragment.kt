package com.renattele.sma1.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.renattele.sma1.data.SurveysRepository
import com.renattele.sma1.databinding.PageSurveyBinding
import com.renattele.sma1.presentation.adapter.SurveyQuestionAdapter
import com.renattele.sma1.utils.CheckListener
import com.renattele.sma1.utils.ScreenTags

class SurveyQuestionFragment: Fragment() {
    private var _binding: PageSurveyBinding? = null
    private val binding get() = _binding!!
    private val position by lazy { arguments?.getInt(POSITION_KEY) ?: 0 }
    private val question get() = SurveysRepository.questions[position]

    private lateinit var adapter: SurveyQuestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = PageSurveyBinding.inflate(inflater)
        with(binding) {
            adapter =
                SurveyQuestionAdapter(question) { answerPosition ->
                    if (!recycler.isComputingLayout) {
                        setAnswerPosition(answerPosition)
                    }
                }
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(requireContext())
            questionTextView.text = question.question
        }
        return binding.root
    }

    private fun setAnswerPosition(answerPosition: Int) {
        val previousSelectedAnswer = adapter.question.selectedAnswer
        adapter.question = adapter.question.copy(selectedAnswer = answerPosition)
        adapter.notifyItemChanged(answerPosition, true)
        if (previousSelectedAnswer != null) {
            adapter.notifyItemChanged(previousSelectedAnswer, false)
        }
        SurveysRepository.update {
            this[position] = this[position].copy(selectedAnswer = answerPosition)
        }
        val surveyFragment = parentFragmentManager.findFragmentByTag(ScreenTags.SURVEY_TAG)
        (surveyFragment as? CheckListener)?.onCheck(position)
    }

    companion object {
        private const val POSITION_KEY = "POSITION"
        fun newInstance(position: Int) = SurveyQuestionFragment().apply {
            arguments = bundleOf(POSITION_KEY to position)
        }
    }
}
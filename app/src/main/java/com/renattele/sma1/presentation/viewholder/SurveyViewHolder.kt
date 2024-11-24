package com.renattele.sma1.presentation.viewholder

import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.renattele.sma1.databinding.PageSurveyBinding
import com.renattele.sma1.domain.SurveyQuestion

class SurveyViewHolder(private val binding: PageSurveyBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(question: SurveyQuestion, onAnswer: (answer: Int) -> Unit) {
        with(binding) {
            this.question.text = question.question
            questionGroup.clearCheck()
            questionGroup.removeAllViews()
            question.answers.forEachIndexed { index, answer ->
                val button = RadioButton(binding.root.context).apply {
                    text = answer
                    setOnCheckedChangeListener { _, _ ->
                        onAnswer(index)
                    }
                }
                questionGroup.addView(button)
                if (question.selectedAnswer == index) {
                    questionGroup.check(button.id)
                }
            }
        }
    }
}
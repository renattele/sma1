package com.renattele.sma1.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renattele.sma1.databinding.ItemSurveyQuestionBinding
import com.renattele.sma1.domain.SurveyQuestion
import com.renattele.sma1.presentation.viewholder.SurveyQuestionAnswerViewHolder

class SurveyQuestionAdapter(
    var question: SurveyQuestion,
    private val onCheck: (position: Int) -> Unit,
) : RecyclerView.Adapter<SurveyQuestionAnswerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SurveyQuestionAnswerViewHolder {
        return SurveyQuestionAnswerViewHolder(
            ItemSurveyQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onCheck = onCheck
        )
    }

    override fun getItemCount(): Int = question.answers.size

    override fun onBindViewHolder(holder: SurveyQuestionAnswerViewHolder, position: Int) {
        holder.bind(question.answers[position])
        holder.bindChecked(question.selectedAnswer == position)
    }

    override fun onBindViewHolder(
        holder: SurveyQuestionAnswerViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            when (payloads.first()) {
                is Boolean -> holder.bindChecked(payloads.first() as Boolean)
                else -> super.onBindViewHolder(holder, position, payloads)
            }
        }
    }
}
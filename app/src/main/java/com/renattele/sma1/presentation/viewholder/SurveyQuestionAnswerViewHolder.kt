package com.renattele.sma1.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.renattele.sma1.R
import com.renattele.sma1.databinding.ItemSurveyQuestionBinding

class SurveyQuestionAnswerViewHolder(
    private val binding: ItemSurveyQuestionBinding,
    private val onCheck: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.button.setOnCheckedChangeListener { _, _ ->
            onCheck(adapterPosition)
        }
    }

    fun bind(answer: String) {
        binding.button.text = answer
    }

    fun bindChecked(checked: Boolean) {
        binding.button.isChecked = checked
        val background =
            if (checked) android.R.color.system_tertiary_dark
            else android.R.color.transparent
        binding.button.setBackgroundResource(background)
    }
}
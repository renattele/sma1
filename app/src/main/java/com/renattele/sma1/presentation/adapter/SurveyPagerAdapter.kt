package com.renattele.sma1.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.renattele.sma1.databinding.PageSurveyBinding
import com.renattele.sma1.domain.SurveyQuestion
import com.renattele.sma1.presentation.viewholder.ListDiffUtilCallback
import com.renattele.sma1.presentation.viewholder.SurveyViewHolder

class SurveyPagerAdapter(
    private val onAnswer: (SurveyQuestion, answer: Int) -> Unit,
    items: List<SurveyQuestion>,
) : RecyclerView.Adapter<SurveyViewHolder>() {

    private val data = mutableListOf<SurveyQuestion>()

    init {
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyViewHolder {
        val binding = PageSurveyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SurveyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SurveyViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item) { answer ->
            onAnswer(item, answer)
        }
    }

    fun update(newData: List<SurveyQuestion>) {
        val callback = ListDiffUtilCallback(
            data, newData,
            same = { t1, t2 -> t1.id == t2.id }
        ) { t1, t2 -> t1.id == t2.id }
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newData)
    }
}
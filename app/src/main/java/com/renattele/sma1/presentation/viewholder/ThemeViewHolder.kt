package com.renattele.sma1.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.renattele.sma1.databinding.ItemThemeBinding
import com.renattele.sma1.domain.Theme

class ThemeViewHolder(
    private val binding: ItemThemeBinding,
    private val onClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.colorView.setOnClickListener {
            onClick(adapterPosition)
        }
    }
    fun bind(theme: Theme) {
        binding.colorView.setBackgroundResource(theme.color)
    }
}
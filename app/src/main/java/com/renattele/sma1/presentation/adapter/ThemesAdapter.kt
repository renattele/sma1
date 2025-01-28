package com.renattele.sma1.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renattele.sma1.databinding.ItemThemeBinding
import com.renattele.sma1.domain.Theme
import com.renattele.sma1.presentation.viewholder.ThemeViewHolder
import com.renattele.sma1.utils.attachBinding

class ThemesAdapter(
    private val themes: List<Theme>,
    private val onClick: (position: Int) -> Unit,
) : RecyclerView.Adapter<ThemeViewHolder>() {
    override fun getItemCount(): Int = themes.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        return ThemeViewHolder(
            parent.attachBinding(ItemThemeBinding::inflate),
            onClick
        )
    }

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        holder.bind(themes[position])
    }
}
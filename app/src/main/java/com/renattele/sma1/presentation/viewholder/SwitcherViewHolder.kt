package com.renattele.sma1.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.renattele.sma1.databinding.ItemHolderSwitcherBinding
import com.renattele.sma1.domain.ListSwitcherEntity

class SwitcherViewHolder(
    private val viewBinding: ItemHolderSwitcherBinding,
) : RecyclerView.ViewHolder(viewBinding.root) {
    @set:Synchronized
    private var _onClick: () -> Unit = {}

    init {
        viewBinding.button.setOnClickListener { _onClick() }
    }
    fun bind(entity: ListSwitcherEntity, onClick: () -> Unit) {
        with(viewBinding) {
            button.text = entity.type.toString()
            _onClick = onClick
        }
    }
}
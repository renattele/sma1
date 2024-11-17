package com.renattele.sma1.presentation.viewholder

import androidx.recyclerview.widget.DiffUtil

class ListDiffUtilCallback<T>(
    private val old: List<T>,
    private val new: List<T>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = old.size
    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] === new[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }
}
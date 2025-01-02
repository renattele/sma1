package com.renattele.sma1.presentation.viewholder

import androidx.recyclerview.widget.DiffUtil

class ListDiffUtilCallback<T>(
    private val old: List<T>,
    private val new: List<T>,
    private val same: (T, T) -> Boolean = { t1, t2 -> t1 === t2 },
    private val changePayload: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
    private val equals: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = old.size
    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return same(old[oldItemPosition], new[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return equals(old[oldItemPosition], new[newItemPosition])
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return changePayload(old[oldItemPosition], new[newItemPosition])
    }
}
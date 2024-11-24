package com.renattele.sma1.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.renattele.sma1.R
import com.renattele.sma1.databinding.ItemHolderProfileBinding
import com.renattele.sma1.databinding.ItemHolderSwitcherBinding
import com.renattele.sma1.domain.ListSwitcherEntity
import com.renattele.sma1.domain.MultiEntity
import com.renattele.sma1.domain.ProfileEntity
import com.renattele.sma1.presentation.viewholder.ListDiffUtilCallback
import com.renattele.sma1.presentation.viewholder.ProfileViewHolder
import com.renattele.sma1.presentation.viewholder.SwitcherViewHolder

class MultiAdapter(
    private val onClick: (MultiEntity) -> Unit,
    private val onLongClick: (MultiEntity) -> Unit,
    private val requestManager: RequestManager,
    items: List<MultiEntity>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data = mutableListOf<MultiEntity>()

    init {
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_holder_profile -> {
                val binding =
                    ItemHolderProfileBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ProfileViewHolder(
                    viewBinding = binding,
                    requestManager = requestManager
                )
            }

            R.layout.item_holder_switcher -> {
                val binding =
                    ItemHolderSwitcherBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                SwitcherViewHolder(
                    viewBinding = binding
                )
            }

            else -> throw IllegalArgumentException("Not supported viewType: $viewType")
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = data[position]) {
            is ProfileEntity -> (holder as? ProfileViewHolder)?.bind(item, { onLongClick(item) }) {
                onClick(item)
            }

            is ListSwitcherEntity -> (holder as? SwitcherViewHolder)?.bind(item) {
                onClick(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = data[position]
        return when (item) {
            is ListSwitcherEntity -> R.layout.item_holder_switcher
            is ProfileEntity -> R.layout.item_holder_profile
        }
    }

    fun update(newData: List<MultiEntity>) {
        val callback = ListDiffUtilCallback(
            data, newData
        )
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newData)
    }

}
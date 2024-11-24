package com.renattele.sma1.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.renattele.sma1.databinding.ItemHolderProfileBinding
import com.renattele.sma1.domain.ProfileEntity

class ProfileViewHolder(
    private val viewBinding: ItemHolderProfileBinding,
    private val requestManager: RequestManager,
) : RecyclerView.ViewHolder(viewBinding.root) {
    @set:Synchronized
    private var _onClick = {}

    @set:Synchronized
    private var _onLongClick = {}

    init {
        viewBinding.root.setOnClickListener { _onClick() }
        viewBinding.root.setOnLongClickListener {
            _onLongClick()
            true
        }
    }

    fun bind(entity: ProfileEntity, onLongClick: () -> Unit, onClick: () -> Unit) {
        with(viewBinding) {
            name.text = entity.name
            requestManager.load(entity.pictureUrl).into(profilePicture)
            _onClick = onClick
            _onLongClick = onLongClick
        }
    }
}
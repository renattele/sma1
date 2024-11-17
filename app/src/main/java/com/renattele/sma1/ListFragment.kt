package com.renattele.sma1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.renattele.sma1.presentation.adapter.MultiAdapter
import com.renattele.sma1.data.ProfilesRepository
import com.renattele.sma1.databinding.FragmentListBinding
import com.renattele.sma1.domain.ListSwitcherEntity
import com.renattele.sma1.domain.ListType
import com.renattele.sma1.domain.MultiEntity
import com.renattele.sma1.domain.ProfileEntity
import com.renattele.sma1.presentation.decorator.SpacingDecoration
import com.renattele.sma1.presentation.touchhelper.SwipeDeleteTouchHelper
import com.renattele.sma1.utils.NavigationAction
import com.renattele.sma1.utils.ScreenTags

class ListFragment : Fragment(), BottomSheetFragment.ReturnCallback {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val recyclerAdapter: MultiAdapter by lazy {
        MultiAdapter(
            requestManager = requireContext().glide,
            onClick = ::onItemClick,
            onLongClick = ::onItemLongClick,
            items = getItems()
        )
    }

    private val buttons = ListType.entries
        .map(::ListSwitcherEntity)

    private fun getItems() = buttons + ProfilesRepository.profiles

    private var listType = ListType.List
        set(value) {
            field = value
            if (value == ListType.List) {
                swipeDeleteTouchHelper.attachToRecyclerView(binding.profiles)
            } else {
                swipeDeleteTouchHelper.attachToRecyclerView(null)
            }
        }

    private val swipeDeleteTouchHelper by lazy {
        SwipeDeleteTouchHelper { index ->
            ProfilesRepository.update {
                removeAt(index - 3)
            }
            updateItems()
        }
    }

    private fun spanSizeFrom(position: Int): Int {
        return when (position) {
            in 0..2 -> 2
            else -> when (listType) {
                ListType.List -> 6
                ListType.Grid -> 2
                ListType.SemiGrid -> {
                    if (position % 4 == 3 ||
                        position % 4 == 2
                    ) 6 else 3
                }
            }
        }
    }

    private fun gridLayoutManager(
        spanCount: Int = 6,
        lookup: (position: Int) -> Int = ::spanSizeFrom,
    ): GridLayoutManager {
        return GridLayoutManager(
            requireContext(),
            spanCount
        ).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return lookup(position)
                }
            }
        }
    }

    private fun onItemClick(item: MultiEntity) {
        when (item) {
            is ListSwitcherEntity -> {
                onListTypeSwitcherClick(item)
            }

            is ProfileEntity -> {
                onProfileClick(item)
            }
        }
    }

    private fun onProfileClick(item: MultiEntity) {
        parentFragmentManager.apply {
            requireActivity().baseActivity().navigate(
                destination = DetailFragment.newInstance(item.id),
                destinationTag = ScreenTags.DETAIL_TAG,
                action = NavigationAction.REPLACE
            )
        }
    }

    private fun onListTypeSwitcherClick(item: ListSwitcherEntity) {
        listType = item.type
        binding.profiles.layoutManager = gridLayoutManager()
    }

    private fun onItemLongClick(item: MultiEntity) {
        if (item is ProfileEntity && listType == ListType.Grid) {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.delete_question, item.name))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    ProfilesRepository.update {
                        removeAll { it.id == item.id }
                    }
                    updateItems()
                }
                .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater)
        with(binding) {
            profiles.apply {
                adapter = recyclerAdapter
                layoutManager = gridLayoutManager()
                addItemDecoration(SpacingDecoration(12))
                swipeDeleteTouchHelper.attachToRecyclerView(this)
            }
            addFab.setOnClickListener {
                val fragment = BottomSheetFragment()
                fragment.show(childFragmentManager, fragment.tag)
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        updateItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBottomSheetResult(change: Int) {
        val profiles = ProfilesRepository.profiles
        if (change > 0) {
            (0 until change)
                .map { ProfilesRepository.generateProfile() }
                .forEach { entity ->
                    val index = if (profiles.isEmpty()) 0
                    else profiles.indices.random()
                    profiles
                        .add(index, entity)
                }
        } else {
            repeat(-change) {
                if (profiles.isNotEmpty()) {
                    val index = profiles.indices.random()
                    profiles.removeAt(index)
                }
            }
        }
        updateItems()
    }

    private fun updateItems() {
        recyclerAdapter.update(getItems())
    }
}
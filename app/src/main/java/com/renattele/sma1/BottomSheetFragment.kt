package com.renattele.sma1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.renattele.sma1.databinding.FragmentBottomSheetBinding

class BottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    interface ReturnCallback {
        fun onBottomSheetResult(change: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        with(binding) {
            binding.textField.doOnTextChanged { _, _, _, _ ->
                val enabled = textField.text.isDigitsOnly()
                createMultipleButton.isEnabled = enabled
                deleteMultipleButton.isEnabled = enabled
            }
            val callback = (parentFragment as ReturnCallback)
            createButton.setOnClickListener {
                callback.onBottomSheetResult(1)
            }
            deleteButton.setOnClickListener {
                callback.onBottomSheetResult(-1)
            }
            createMultipleButton.setOnClickListener {
                val count = textField.text.toString().toIntOrNull()
                if (count != null) callback.onBottomSheetResult(count)
            }
            deleteMultipleButton.setOnClickListener {
                val count = textField.text.toString().toIntOrNull()
                if (count != null) callback.onBottomSheetResult(-count)
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
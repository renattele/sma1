package com.renattele.sma1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.renattele.sma1.databinding.FragmentBottomSheetBinding

class BottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    interface ReturnData {
        fun get(text: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        println(parentFragment)
        binding.textField.doOnTextChanged { _, _, _, _ ->
            binding.saveButton.isEnabled = binding.textField.text.isNotEmpty()
        }
        binding.saveButton.setOnClickListener {
            val text = binding.textField.text.toString()
            (parentFragment as ReturnData).get(text)
            dismiss()
        }
        return binding.root
    }
}
package com.renattele.sma1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.renattele.sma1.databinding.FragmentSecondBinding
import com.renattele.sma1.databinding.FragmentThirdBinding

private const val ARG_TEXT = "text"
class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!
    private val textArgument
        get() = arguments?.getString(ARG_TEXT)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(layoutInflater)
        binding.text.apply {
            text = if (textArgument == null) {
                context.getString(R.string.no_data_provided)
            } else {
                context.getString(R.string.data, textArgument)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(text: String? = null): ThirdFragment {
            return ThirdFragment().apply {
                arguments = bundleOf(ARG_TEXT to text)
            }
        }
    }
}
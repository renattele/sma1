package com.renattele.sma1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.renattele.sma1.databinding.FragmentFirstBinding

class FirstFragment : Fragment(), BottomSheetFragment.ReturnData {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(layoutInflater)
        binding.goToSecondButton.setOnClickListener {
            navigate { text ->
                SecondFragment.newInstance(text)
            }
        }
        binding.goToThirdButton.setOnClickListener {
            navigate { text ->
                SecondFragment.newInstance(text)
            }
            navigate { text ->
                ThirdFragment.newInstance(text)
            }
        }
        binding.goToBottomSheetButton.setOnClickListener {
            val fragment = BottomSheetFragment()
            fragment.show(childFragmentManager, fragment.tag)
        }
        return binding.root
    }

    private fun navigate(fragment: (text: String?) -> Fragment) {
        parentFragmentManager.apply {
            val text = binding.textField.text.toString()
            val instance = fragment(text.takeIf { it.isNotEmpty() })
            beginTransaction()
                .replace(R.id.fragment_container_view, instance)
                .addToBackStack(instance.toString())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun get(text: String) {
        binding.textField.setText(text)
    }
}
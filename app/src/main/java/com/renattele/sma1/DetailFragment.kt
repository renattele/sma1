package com.renattele.sma1;


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import com.renattele.sma1.data.ProfilesRepository
import com.renattele.sma1.databinding.FragmentDetailBinding
import com.renattele.sma1.domain.ProfileEntity

private const val ARG_ID = "id"

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val id
        get() = arguments?.getString(ARG_ID)

    // not using lazy because profile can be changed during screen is opened
    private val profile: ProfileEntity?
        get() = ProfilesRepository.profiles.find { it.id == id }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        binding.apply {
            if (profile == null) {
                bioField.setText(getString(R.string.not_found_this_id, id))
                bioField.isEnabled = false
                nameField.setText(getString(R.string.not_found_this_id, id))
                nameField.isEnabled = false
            } else {
                val entity = profile!!
                requireContext().glide.load(entity.pictureUrl).into(profilePicture)
                nameField.setText(entity.name)
                bioField.setText(entity.bio)
                nameField.doAfterTextChanged {
                    val text = nameField.text.toString()
                    val updatedProfile = profile?.copy(
                        name = text
                    )
                    if (updatedProfile != null) updateWith(updatedProfile)
                }
                bioField.doAfterTextChanged {
                    val bio = bioField.text.toString()
                    val updatedProfile = profile?.copy(
                        bio = bio
                    )
                    if (updatedProfile != null) updateWith(updatedProfile)
                }
            }
        }
        return binding.root
    }

    private fun updateWith(newProfile: ProfileEntity) {
        ProfilesRepository.update {
            val index = indexOfFirst { it.id == newProfile.id }
            if (index != -1) {
                this[index] = newProfile
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(id: String? = null): DetailFragment {
            return DetailFragment().apply {
                arguments = bundleOf(ARG_ID to id)
            }
        }
    }
}
package com.example.recyclerviewwithnavigationcomponent.ui.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewwithnavigationcomponent.MyApplication
import com.example.recyclerviewwithnavigationcomponent.R
import com.example.recyclerviewwithnavigationcomponent.databinding.FragmentEditProfileBinding
import com.example.recyclerviewwithnavigationcomponent.ui.main.SharedViewModel

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: SharedViewModel by activityViewModels<SharedViewModel> {
        (activity?.application as MyApplication).viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEditProfileBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDisplayData()

        binding.btnSaveProfile.setOnClickListener {
            editAccountUserProfile()
        }

        viewModel.errorUpdateUserProfile.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showDisplayData() {
        viewModel.userProfile.observe(viewLifecycleOwner) {
            binding.usernameProfile.text = it.username
        }
    }

    private fun editAccountUserProfile() {
        if (binding.edtUsernameInEditProfile.text.isNullOrEmpty()) binding.itlUsernameInEditProfile.error =
            "username Empty"
        if (binding.edtEmailInEditProfile.text.isNullOrEmpty()) binding.itlEmailInEditProfile.error =
            "Email Empty"
        if (binding.edtPasswordInEditProfile.text.isNullOrEmpty()) binding.itlPasswordInEditProfile.error =
            "Password Empty"
        else {
            binding.itlUsernameInEditProfile.error = null
            binding.itlEmailInEditProfile.error = null
            binding.itlPasswordInEditProfile.error = null
            val username = binding.edtUsernameInEditProfile.text.toString()
            val email = binding.edtEmailInEditProfile.text.toString()
            val password = binding.edtPasswordInEditProfile.text.toString()
            viewModel.updateAccountUserProfile(username, email, password)
            findNavController().popBackStack(R.id.profileFragment, false)

        }

    }
}
package com.example.recyclerviewwithnavigationcomponent.ui.mvvm.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewwithnavigationcomponent.R
import com.example.recyclerviewwithnavigationcomponent.databinding.FragmentProfileBinding
import com.example.recyclerviewwithnavigationcomponent.ui.authenctication.AuthenticationActivity
import com.example.recyclerviewwithnavigationcomponent.ui.mvvm.SharedViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by activityViewModels<SharedViewModel> {
        SharedViewModel.provideFactory(this, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentProfileBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userProfile.observe(viewLifecycleOwner) {
            binding.usernameProfileInProfile.text = it.username
            binding.emailInProfile.text = it.email
            binding.tokenInProfile.text = it.token
        }

        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.btnLogoutInProfile.setOnClickListener {
            viewModel.logout()
        }
        viewModel.successLogout.observe(viewLifecycleOwner) {
            if (it) {
                activity?.startActivity(
                    Intent(
                        requireContext(),
                        AuthenticationActivity::class.java
                    )
                )
                activity?.finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshUserProfile()
    }


}
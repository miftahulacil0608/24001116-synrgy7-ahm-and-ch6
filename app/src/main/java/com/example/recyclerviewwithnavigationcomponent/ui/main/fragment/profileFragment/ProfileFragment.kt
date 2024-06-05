package com.example.recyclerviewwithnavigationcomponent.ui.main.fragment.profileFragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.work.WorkInfo
import coil.load
import com.example.recyclerviewwithnavigationcomponent.R
import com.example.recyclerviewwithnavigationcomponent.databinding.FragmentProfileBinding
import com.example.recyclerviewwithnavigationcomponent.ui.authenctication.AuthenticationActivity
import com.example.recyclerviewwithnavigationcomponent.ui.main.fragment.profileFragment.EditProfileFragment.Companion.KEY_REQUEST_IMAGE_PROFILE
import com.example.recyclerviewwithnavigationcomponent.workers.KEY_IMAGE_URI
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by activityViewModel<ProfileViewModel>()


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

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(KEY_REQUEST_IMAGE_PROFILE)?.observe(viewLifecycleOwner){
            binding.profileImageInProfile.load(it)

        }

        viewModel.dataProfile.observe(viewLifecycleOwner) {
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
        viewModel.refreshData()
    }

}
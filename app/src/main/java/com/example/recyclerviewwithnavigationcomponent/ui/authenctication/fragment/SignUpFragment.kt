package com.example.recyclerviewwithnavigationcomponent.ui.authenctication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewwithnavigationcomponent.MyApplication
import com.example.recyclerviewwithnavigationcomponent.R
import com.example.recyclerviewwithnavigationcomponent.databinding.FragmentSignupBinding
import com.example.recyclerviewwithnavigationcomponent.ui.authenctication.AuthenticationViewModel
import com.example.recyclerviewwithnavigationcomponent.viewmodelfactory.ViewModelFactory

class SignUpFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSignupBinding
    private val authViewModel: AuthenticationViewModel by activityViewModels<AuthenticationViewModel> {
        (activity?.application as MyApplication).viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentSignupBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignupInFragmentSignUp.setOnClickListener(this)


        binding.btnSignInInFragmentSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_signup_inFragment_SignUp -> {
                if (binding.edtUsername.text.isNullOrEmpty()) binding.itlUsername.error =
                    "username Empty"
                if (binding.edtEmail.text.isNullOrEmpty()) binding.itlEmail.error = "Email Empty"
                if (binding.edtPassword.text.isNullOrEmpty()) binding.itlPassword.error =
                    "Password Empty"
                else {
                    binding.itlUsername.error = null
                    binding.itlEmail.error = null
                    binding.itlPassword.error = null
                    val username = binding.edtUsername.text.toString()
                    val email = binding.edtEmail.text.toString()
                    val password = binding.edtPassword.text.toString()
                    authViewModel.createAccount(username, email, password)
                    findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                }
            }

            R.id.btn_signIn_inFragment_SignUp -> {
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }
        }
    }

}
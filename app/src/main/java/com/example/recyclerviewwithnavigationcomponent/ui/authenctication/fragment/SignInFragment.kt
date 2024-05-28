package com.example.recyclerviewwithnavigationcomponent.ui.authenctication.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewwithnavigationcomponent.R
import com.example.recyclerviewwithnavigationcomponent.databinding.FragmentSignInBinding
import com.example.recyclerviewwithnavigationcomponent.ui.authenctication.AuthenticationViewModel
import com.example.recyclerviewwithnavigationcomponent.ui.mvvm.MainActivity

class SignInFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSignInBinding

    private val viewModel: AuthenticationViewModel by viewModels<AuthenticationViewModel> {
        AuthenticationViewModel.provideFactory(this, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentSignInBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignInInFragmentSignIn.setOnClickListener(this)
        binding.btnSignUpInFragmentSignIn.setOnClickListener(this)


        viewModel.success.observe(viewLifecycleOwner) { success ->
            if (success) {
                context?.startActivity(
                    Intent(
                        requireActivity(), MainActivity::class.java
                    )
                )
                activity?.finish()
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "error: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_signIn_inFragment_SignIn -> {
                if (binding.edtEmail.text.isNullOrEmpty()) binding.itlEmail.error = "Email Empty"
                if (binding.edtPassword.text.isNullOrEmpty()) binding.itlPassword.error =
                    "Password Empty"
                else {
                    binding.itlEmail.error = null
                    binding.itlPassword.error = null
                    val email = binding.edtEmail.text.toString()
                    val password = binding.edtPassword.text.toString()
                    viewModel.loadDataAccount(email, password)
                }
            }

            R.id.btn_signUp_inFragment_SignIn -> {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }
    }

}
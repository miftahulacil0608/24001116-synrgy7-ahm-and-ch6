package com.example.recyclerviewwithnavigationcomponent.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerviewwithnavigationcomponent.di.Module
import com.example.recyclerviewwithnavigationcomponent.ui.authenctication.AuthenticationViewModel
import com.example.recyclerviewwithnavigationcomponent.ui.main.SharedViewModel
import com.example.recyclerviewwithnavigationcomponent.ui.splashScreen.SplashScreenViewModel

class ViewModelFactory(private val module: Module) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            AuthenticationViewModel::class.java -> {
                AuthenticationViewModel(authenticationRepository = module.authenticationRepository) as T
            }

            SplashScreenViewModel::class.java -> {
                SplashScreenViewModel(authenticationRepository = module.authenticationRepository) as T
            }

            SharedViewModel::class.java -> {
                SharedViewModel(useCase = module.useCase) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
        }
    }
}
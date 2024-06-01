package com.example.recyclerviewwithnavigationcomponent.viewmodelfactory

import com.example.recyclerviewwithnavigationcomponent.ui.authenctication.AuthenticationViewModel
import com.example.recyclerviewwithnavigationcomponent.ui.main.SharedViewModel
import com.example.recyclerviewwithnavigationcomponent.ui.splashScreen.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinViewModelModule = module {
    viewModel{SharedViewModel(useCase = get())}
    viewModel { AuthenticationViewModel(authenticationRepository = get()) }
    viewModel{SplashScreenViewModel(authenticationRepository = get())}
}
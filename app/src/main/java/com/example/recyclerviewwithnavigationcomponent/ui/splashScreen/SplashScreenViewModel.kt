package com.example.recyclerviewwithnavigationcomponent.ui.splashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.recyclerviewwithnavigationcomponent.domain.repository.AuthenticationRepository

class SplashScreenViewModel(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    val loadTokenAccount: LiveData<String?> = liveData {
        val token: MutableLiveData<String?> = MutableLiveData(authenticationRepository.loadToken())
        emitSource(token)
    }
}
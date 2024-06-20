package com.example.recyclerviewwithnavigationcomponent.ui.authenctication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewwithnavigationcomponent.domain.usecase.AuthUseCase
import com.example.recyclerviewwithnavigationcomponent.workers.sleep
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val authUseCase: AuthUseCase) : ViewModel() {


    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private var _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error


    fun createAccount(usernameInput: String, emailInput: String, passwordInput: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                authUseCase.createAccount(usernameInput, emailInput, passwordInput)
                delay(1000)
                _loading.value = false
                _success.value = true
            } catch (throwable: Throwable) {
                _error.value = throwable
            }
        }
    }

    fun loadDataAccount(usernameOrEmailInput: String, passwordInput: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                authUseCase.loadDataAccount(usernameOrEmailInput, passwordInput)
                delay(1000)
                _loading.value = false
                _success.value = true
            } catch (throwable: Throwable) {
                _error.value = throwable
            }
        }
    }
}
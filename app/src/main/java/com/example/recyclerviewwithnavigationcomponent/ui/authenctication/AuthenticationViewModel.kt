package com.example.recyclerviewwithnavigationcomponent.ui.authenctication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewwithnavigationcomponent.domain.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private var _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private var _isLoadingCreateAccount = MutableLiveData<Boolean>()
    val isLoadingCreateAccount: LiveData<Boolean> = _isLoadingCreateAccount

    private var _isSuccessCreateAccount = MutableLiveData<Boolean>()
    val isSuccessCreateAccount: LiveData<Boolean> = _isSuccessCreateAccount

    fun createAccount(usernameInput: String, emailInput: String, passwordInput: String) {
        viewModelScope.launch {
            try {
                _isLoadingCreateAccount.value = true
                authenticationRepository.createAccount(usernameInput, emailInput, passwordInput)
                _isLoadingCreateAccount.value = false
                _isSuccessCreateAccount.value = true
            } catch (throwable: Throwable) {
                _isSuccessCreateAccount.value = false
                throw IllegalAccessError(throwable.message)
            }
        }
    }

    fun loadDataAccount(usernameOrEmailInput: String, passwordInput: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                authenticationRepository.loadDataAccount(usernameOrEmailInput, passwordInput)
                _loading.value = false
                _success.value = true

            } catch (throwable: Throwable) {
                _error.value = throwable
                _success.value = false
                Log.d("Error login", "Message: ${throwable.message}")
            }
        }
    }
}
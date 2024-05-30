package com.example.recyclerviewwithnavigationcomponent.ui.authenctication

import android.content.Context
import android.util.Log
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.example.recyclerviewwithnavigationcomponent.data.LoginDataSource
import com.example.recyclerviewwithnavigationcomponent.data.datasource.local.LocalLoginImpl
import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.RemoteLoginImpl

import com.example.recyclerviewwithnavigationcomponent.data.model.AuthPreferences
import com.example.recyclerviewwithnavigationcomponent.data.model.dataStore
import com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.LocalLoginDataSource
import com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.RemoteLoginDataSource
import com.example.recyclerviewwithnavigationcomponent.domain.repository.LoginRepository
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val loginRepository: LoginRepository) : ViewModel() {

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

    fun createAccount(usernameInput: String, emailInput: String, passwordInput: String){
        viewModelScope.launch {
            try {
                _isLoadingCreateAccount.value = true
                loginRepository.createAccount(usernameInput, emailInput, passwordInput)
                _isLoadingCreateAccount.value = false
                _isSuccessCreateAccount.value = true
            }catch (throwable: Throwable){
                _isSuccessCreateAccount.value = false
                throw IllegalAccessError(throwable.message)
            }
        }
    }

    fun loadDataAccount(usernameOrEmailInput: String, passwordInput: String){
        viewModelScope.launch {
            try {
                _loading.value = true
                loginRepository.loadDataAccount(usernameOrEmailInput, passwordInput)
                _loading.value = false
                _success.value = true

            }catch (throwable: Throwable){
                _error.value = throwable
                _success.value = false
                Log.d("Error login", "Message: ${throwable.message}")
            }
        }
    }

    companion object {
        fun provideFactory(
            owner: SavedStateRegistryOwner,
            context: Context
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, null) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {

                    val authPreferences =
                        AuthPreferences(
                            context.dataStore
                        )
                    val remoteLoginDataSource: RemoteLoginDataSource =
                        RemoteLoginImpl(
                            authPreferences
                        )
                    val localLoginDataSource: LocalLoginDataSource =
                  LocalLoginImpl(
                            /*SharedPreferences().getSharedPreferences(context.applicationContext)*/
                            dataStore = authPreferences
                        )
                    val loginRepository: LoginRepository =
                        LoginDataSource(
                            remoteLoginDataSource = remoteLoginDataSource,
                            localLoginDataSource = localLoginDataSource,
                        )
                    return AuthenticationViewModel(loginRepository = loginRepository) as T
                }
            }
    }
}
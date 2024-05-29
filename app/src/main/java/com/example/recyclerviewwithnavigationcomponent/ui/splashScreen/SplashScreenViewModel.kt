package com.example.recyclerviewwithnavigationcomponent.ui.splashScreen

import android.content.Context
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.savedstate.SavedStateRegistryOwner
import com.example.recyclerviewwithnavigationcomponent.data.LoginDataSource
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.local.LocalLoginImpl
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.RemoteLoginImpl
import com.example.recyclerviewwithnavigationcomponent.data.model.AuthPreferences
import com.example.recyclerviewwithnavigationcomponent.data.model.dataStore
import com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.LocalLoginDataSource
import com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.RemoteLoginDataSource
import com.example.recyclerviewwithnavigationcomponent.domain.repository.LoginRepository

class SplashScreenViewModel(private val loginRepository: LoginRepository) : ViewModel() {

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

                    val authPreferences = AuthPreferences(context.dataStore)
                    val remoteLoginDataSource: RemoteLoginDataSource = RemoteLoginImpl(authPreferences)
                    val localLoginDataSource: LocalLoginDataSource = LocalLoginImpl(
                        /*SharedPreferences().getSharedPreferences(context.applicationContext)*/
                        authPreferences
                    )
                    val loginRepository: LoginRepository = LoginDataSource(
                        remoteLoginDataSource = remoteLoginDataSource,
                        localLoginDataSource = localLoginDataSource,
                    )
                    return SplashScreenViewModel(loginRepository = loginRepository) as T
                }
            }
    }

    /* fun loadToken():String{
         return loginRepository.loadToken()
     }*/

    val loadTokenAccount: LiveData<String?> = liveData {
        val token: MutableLiveData<String?> = MutableLiveData(loginRepository.loadToken())
        emitSource(token)
    }
}
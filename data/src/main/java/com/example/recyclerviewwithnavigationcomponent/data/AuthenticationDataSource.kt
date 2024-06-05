package com.example.recyclerviewwithnavigationcomponent.data

import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.UserProfileData
import com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.LocalAuthenticationDataSource
import com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.RemoteAuthenticationDataSource
import com.example.recyclerviewwithnavigationcomponent.domain.repository.AuthenticationRepository

class AuthenticationDataSource(private val remoteAuthenticationDataSource: RemoteAuthenticationDataSource, private val localAuthenticationDataSource: LocalAuthenticationDataSource):
    AuthenticationRepository {
    override suspend fun createAccount(username: String, email: String, password: String) {
        remoteAuthenticationDataSource.createAccount(username,email,password)
    }

    override suspend fun loadDataAccount(
        usernameOrEmailInput: String,
        passwordInput: String
    ) {
        remoteAuthenticationDataSource.loadDataAccount(usernameOrEmailInput,passwordInput)
    }

    override suspend fun loadToken(): String? {
        return localAuthenticationDataSource.loadAccountToken()
    }


    override suspend fun clearDataAccount() {
        localAuthenticationDataSource.clearAccount()
    }

    override suspend fun getAllDataUserProfile(): UserProfileData {
       return localAuthenticationDataSource.getAllDataUserProfile()
    }

    override suspend fun updateUserProfile(username: String, email: String, password: String) {
        localAuthenticationDataSource.updateDataUserProfile(username,email,password)
    }

}
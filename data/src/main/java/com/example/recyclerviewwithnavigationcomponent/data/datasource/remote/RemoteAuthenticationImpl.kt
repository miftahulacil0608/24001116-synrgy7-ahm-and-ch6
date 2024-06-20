package com.example.recyclerviewwithnavigationcomponent.data.datasource.remote

import com.example.recyclerviewwithnavigationcomponent.data.model.AuthPreferences
import com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.RemoteAuthenticationDataSource

class RemoteAuthenticationImpl(private val dataStore: AuthPreferences) :
    RemoteAuthenticationDataSource {

    override suspend fun createAccount(
        usernameInput: String,
        emailInput: String,
        passwordInput: String
    ) {
        dataStore.createAndUpdateAccount(usernameInput, emailInput, passwordInput)
    }

    override suspend fun loadDataAccount(
        usernameOrEmailInput: String,
        passwordInput: String
    ) {
        dataStore.loadDataAccount(usernameOrEmailInput, passwordInput)
    }
}
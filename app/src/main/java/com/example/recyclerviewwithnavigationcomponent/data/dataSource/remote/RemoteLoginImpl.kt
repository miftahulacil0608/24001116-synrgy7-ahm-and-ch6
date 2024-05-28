package com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote

import com.example.recyclerviewwithnavigationcomponent.data.model.AuthPreferences
import com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.RemoteLoginDataSource

class RemoteLoginImpl(private val dataStore: AuthPreferences) : RemoteLoginDataSource {

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
        return dataStore.loadDataAccount(usernameOrEmailInput, passwordInput)
    }
}
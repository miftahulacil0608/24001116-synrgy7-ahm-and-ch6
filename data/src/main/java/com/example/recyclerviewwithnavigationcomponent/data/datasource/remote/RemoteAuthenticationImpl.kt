package com.example.recyclerviewwithnavigationcomponent.data.datasource.remote

class RemoteAuthenticationImpl(private val dataStore: com.example.recyclerviewwithnavigationcomponent.data.model.AuthPreferences) :
    com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.RemoteAuthenticationDataSource {

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
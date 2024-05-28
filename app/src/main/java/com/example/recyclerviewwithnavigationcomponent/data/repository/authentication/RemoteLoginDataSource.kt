package com.example.recyclerviewwithnavigationcomponent.data.repository.authentication

interface RemoteLoginDataSource {
    suspend fun createAccount(usernameInput:String, emailInput:String,passwordInput:String)
    suspend fun loadDataAccount(usernameOrEmailInput:String, passwordInput:String)
}
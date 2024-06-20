package com.example.recyclerviewwithnavigationcomponent.domain.usecase

import com.example.recyclerviewwithnavigationcomponent.domain.repository.AuthenticationRepository

class AuthUseCase(private val authRepository: AuthenticationRepository) {
    suspend fun createAccount(username:String,email: String, password: String) {
        authRepository.createAccount(username,email, password)
    }

    suspend fun loadDataAccount(usernameOrEmailInput: String, passwordInput: String) {
        authRepository.loadDataAccount(usernameOrEmailInput,passwordInput)
    }
}
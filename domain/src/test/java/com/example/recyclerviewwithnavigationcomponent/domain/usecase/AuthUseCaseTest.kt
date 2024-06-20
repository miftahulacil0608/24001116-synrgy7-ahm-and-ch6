package com.example.recyclerviewwithnavigationcomponent.domain.usecase

import com.example.recyclerviewwithnavigationcomponent.domain.repository.AuthenticationRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class AuthUseCaseTest {

    val authenticationRepository = mock<AuthenticationRepository>()
    val authUseCase = AuthUseCase(authenticationRepository)

    @Test
    fun createAccount() = runTest {
        val username = "username"
        val email = "email@gmail.com"
        val password = "password"

        whenever(authenticationRepository.createAccount(username, email, password)).thenReturn(Unit)
        authUseCase.createAccount(username, email, password)
        verify(authenticationRepository).createAccount(username, email, password)
    }

    @Test
    fun loadDataAccount()= runTest {
        val usernameOrEmail = "username"
        val password = "password"
        whenever(authenticationRepository.loadDataAccount(usernameOrEmail, password)).thenReturn(Unit)
        authUseCase.loadDataAccount(usernameOrEmail, password)
        verify(authenticationRepository).loadDataAccount(usernameOrEmail, password)
    }
}
package com.example.recyclerviewwithnavigationcomponent.data.datasource.remote

import com.example.recyclerviewwithnavigationcomponent.data.model.AuthPreferences
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RemoteAuthenticationImplTest {

    //given
    private val authPreferences = mock<AuthPreferences>()
    private val remoteAuthenticationImpl = RemoteAuthenticationImpl(authPreferences)

    @Test
    fun createAccount() = runTest{
        //given
        val username = "username"
        val email = "miftahulacil@gmail.com"
        val password = "password"

        //when
        remoteAuthenticationImpl.createAccount(username,email,password)

        //then
        verify(authPreferences).createAndUpdateAccount(username,email,password)

    }

    @Test
    fun loadDataAccount() = runTest {
        //given
        val username = "username"
        val password = "password"

        //when
        remoteAuthenticationImpl.loadDataAccount(username,password)


        //then
        verify(authPreferences).loadDataAccount(username,password)
    }
}
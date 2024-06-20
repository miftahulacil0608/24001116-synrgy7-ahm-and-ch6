package com.example.recyclerviewwithnavigationcomponent.data.datasource.local

import com.example.recyclerviewwithnavigationcomponent.data.model.AuthPreferences
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.UserProfileData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocalAuthenticationImplTest {

    //Given
    private val authPreferences : AuthPreferences = mock<AuthPreferences>()
    private val localAuthenticationImpl = LocalAuthenticationImpl(authPreferences)

    @Test
    fun loadAccountToken()= runTest{
        //given

        val responseToken = "token"

        //When

        whenever(authPreferences.loadToken()).thenReturn(responseToken)

        val expectedToken = "token"
        val actual = localAuthenticationImpl.loadAccountToken()

        //then
        assertEquals(expectedToken,actual)
    }

    @Test
    fun clearAccount() = runTest {
        //when
        localAuthenticationImpl.clearAccount()
        //then
        verify(authPreferences).clearDataAccount()
    }

    @Test
    fun getAllDataUserProfile() = runTest {
        //given
        val username = "username"
        val email = "miftahulacil0608@gmail.com"
        val password = "password"
        val token = "token"
        val response = UserProfileData(username,email,password,token)

        //when
        whenever(authPreferences.getAllData()).thenReturn(response)
        val responseResult = localAuthenticationImpl.getAllDataUserProfile()

        //then
        assertEquals(response,responseResult)
    }

    @Test
    fun updateDataUserProfile() = runTest {
        //given
        val username = "username"
        val email = "miftahulacil0608@gmail.com"
        val password = "password"

        //when
        localAuthenticationImpl.updateDataUserProfile(username,email,password)

        //then
        verify(authPreferences).createAndUpdateAccount(username,email,password)
    }
}
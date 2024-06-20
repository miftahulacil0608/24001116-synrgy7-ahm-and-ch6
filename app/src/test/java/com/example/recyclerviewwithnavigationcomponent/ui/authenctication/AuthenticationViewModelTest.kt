package com.example.recyclerviewwithnavigationcomponent.ui.authenctication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.recyclerviewwithnavigationcomponent.MainDispatcherRule
import com.example.recyclerviewwithnavigationcomponent.domain.usecase.AuthUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class AuthenticationViewModelTest {

    //yang menjalankan viewmodel scopenya
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule: MainDispatcherRule = MainDispatcherRule()

    private val authenticationRepository: AuthUseCase = mock()
    private val authenticationViewModel = AuthenticationViewModel(authenticationRepository)

    private val loadingObserver = mock<Observer<Boolean>>()
    private val errorObserver = mock<Observer<Throwable>>()
    private val successObserver = mock<Observer<Boolean>>()

    private val loadingCaptor = argumentCaptor<Boolean>()
    private val errorCaptor = argumentCaptor<Throwable>()
    private val successCaptor = argumentCaptor<Boolean>()

    @Test
    fun createAccount_success() = runTest {
        //GIVEN
        val username = "username"
        val password = "password"
        val email = "email@gmail.com"

        //WHEN
        //data real liveData
        val loadingLiveData = authenticationViewModel.loading
        val errorLiveData = authenticationViewModel.error
        val successLiveData = authenticationViewModel.success

        //dilakukan observer ke dalam value yang di mock
        loadingLiveData.observeForever(loadingObserver)
        errorLiveData.observeForever(errorObserver)
        successLiveData.observeForever(successObserver)

        whenever(authenticationRepository.createAccount(username, email, password)).thenReturn(Unit)
        authenticationViewModel.createAccount(username, email, password)
        //untuk menanggani delay yang ada di dalam viewmodelnya
        advanceUntilIdle()

        //THEN
        //dilakukan verify ke dalam value yang di mock menggunakan captor sebagai nilai real
        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        verify(errorObserver, times(0)).onChanged(errorCaptor.capture())
        verify(successObserver).onChanged(successCaptor.capture())

        Assert.assertEquals(loadingCaptor.allValues, listOf(true, false))
        Assert.assertEquals(successCaptor.firstValue, true)
    }

    @Test
    fun createAccount_failed() = runTest {
        //given
        val username = "username"
        val password = "password"
        val email = "email@gmail.com"
        val errorException = IllegalAccessError("Error Creating Account")

        //when
        //data real liveData
        val errorLiveData = authenticationViewModel.error
        //dilakukan observer ke dalam value yang di mock
        errorLiveData.observeForever(errorObserver)

        whenever(
            authenticationRepository.createAccount(
                username,
                email,
                password
            )
        ).thenAnswer { throw errorException }
        authenticationViewModel.createAccount(username, email, password)
        //untuk menanggani delay yang ada di dalam viewmodelnya
        advanceUntilIdle()

        //then
        verify(errorObserver).onChanged(errorCaptor.capture())
        Assert.assertEquals(errorCaptor.firstValue.message, errorException.message)


    }

    @Test
    fun loadDataAccount_success() = runTest {
        //GIVEN
        val usernameOrEmailInput = "username"
        val password = "password"

        //WHEN
        val loadingLiveData = authenticationViewModel.loading
        val errorLiveData = authenticationViewModel.error
        val successLiveData = authenticationViewModel.success

        loadingLiveData.observeForever(loadingObserver)
        errorLiveData.observeForever(errorObserver)
        successLiveData.observeForever(successObserver)

        whenever(authenticationRepository.loadDataAccount(usernameOrEmailInput,password)).thenReturn(Unit)
        authenticationViewModel.loadDataAccount(usernameOrEmailInput,password)
        advanceUntilIdle()

        //THEN
        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        verify(errorObserver, times(0)).onChanged(errorCaptor.capture())
        verify(successObserver).onChanged(successCaptor.capture())

        Assert.assertEquals(loadingCaptor.allValues, listOf(true, false))
        Assert.assertEquals(successCaptor.firstValue, true)
    }
    @Test
    fun loadDataAccount_failed() = runTest {
        //GIVEN
        val usernameOrEmailInput = "username"
        val password = "password"
        val errorException = IllegalAccessError("Error Loading Data Account")

        //WHEN
        val loadingLiveData = authenticationViewModel.loading
        val errorLiveData = authenticationViewModel.error
        val successLiveData = authenticationViewModel.success

        loadingLiveData.observeForever(loadingObserver)
        errorLiveData.observeForever(errorObserver)
        successLiveData.observeForever(successObserver)

        whenever(authenticationRepository.loadDataAccount(usernameOrEmailInput,password)).thenAnswer{throw errorException}
        authenticationViewModel.loadDataAccount(usernameOrEmailInput,password)
        advanceUntilIdle()

        //THEN
        verify(errorObserver).onChanged(errorCaptor.capture())
        Assert.assertEquals(errorCaptor.firstValue.message, errorException.message)

    }
}
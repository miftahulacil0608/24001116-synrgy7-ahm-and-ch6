package com.example.recyclerviewwithnavigationcomponent.domain

import androidx.lifecycle.LiveData
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.UserProfileData

interface LoginRepository {
    /*suspend fun login(email:String,password:String):String
    fun saveToken(dataToken:String)
    fun loadToken():String?
    fun clearToken()*/

    suspend fun createAccount(username:String,email:String,password:String)
    suspend fun loadDataAccount(usernameOrEmailInput:String,passwordInput:String)
    suspend fun loadToken():String?
    suspend fun clearDataAccount()
    suspend fun getAllDataUserProfile():UserProfileData
    suspend fun updateUserProfile(username: String,email:String,password: String)
}
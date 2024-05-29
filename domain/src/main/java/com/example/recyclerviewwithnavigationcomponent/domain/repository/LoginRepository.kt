package com.example.recyclerviewwithnavigationcomponent.domain.repository
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.UserProfileData

interface LoginRepository {
    suspend fun createAccount(username:String,email:String,password:String)
    suspend fun loadDataAccount(usernameOrEmailInput:String,passwordInput:String)
    suspend fun loadToken():String?
    suspend fun clearDataAccount()
    suspend fun getAllDataUserProfile(): UserProfileData
    suspend fun updateUserProfile(username: String,email:String,password: String)
}
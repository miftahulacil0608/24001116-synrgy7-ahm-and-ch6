package com.example.recyclerviewwithnavigationcomponent.data.repository.authentication

import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.UserProfileData

interface LocalLoginDataSource {
    suspend fun loadAccountToken():String?
    suspend fun clearAccount()
    suspend fun getAllDataUserProfile(): UserProfileData
    suspend fun updateDataUserProfile(username:String,email:String,password:String)
}
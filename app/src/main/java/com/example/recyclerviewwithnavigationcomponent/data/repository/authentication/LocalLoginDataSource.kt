package com.example.recyclerviewwithnavigationcomponent.data.repository.authentication

import androidx.lifecycle.LiveData
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.UserProfileData

interface LocalLoginDataSource {
    suspend fun loadAccountToken():String?
    suspend fun clearAccount()
    suspend fun getAllDataUserProfile():UserProfileData
    suspend fun updateDataUserProfile(username:String,email:String,password:String)
}
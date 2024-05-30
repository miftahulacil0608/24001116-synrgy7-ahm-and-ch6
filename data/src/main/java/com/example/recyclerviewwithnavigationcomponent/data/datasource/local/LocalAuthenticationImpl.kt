package com.example.recyclerviewwithnavigationcomponent.data.datasource.local

import com.example.recyclerviewwithnavigationcomponent.data.model.AuthPreferences
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.UserProfileData
import com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.LocalAuthenticationDataSource

class LocalAuthenticationImpl(private val dataStore: AuthPreferences) : LocalAuthenticationDataSource {

    override suspend fun loadAccountToken(): String? {
        return dataStore.loadToken()
    }

    override suspend fun clearAccount() {
        dataStore.clearDataAccount()
    }

    override suspend fun getAllDataUserProfile(): UserProfileData {
        return dataStore.getAllData()
    }

    override suspend fun updateDataUserProfile(username: String, email: String, password: String) {
        dataStore.createAndUpdateAccount(username, email, password)
    }

}
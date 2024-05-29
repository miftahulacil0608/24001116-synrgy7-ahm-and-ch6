package com.example.recyclerviewwithnavigationcomponent.data.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.UserProfileData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID

//tahap 1 (constructor parameternya harus berisi singleteon context dataStore), jika kamu isi dengan hanya context dia akan dibilang duplikast apabila diinisasliasi dibuat object di kelas lain
class AuthPreferences(private val dataStore: DataStore<Preferences>) {


    //tahap 2
    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
        val PASSWORD_KEY = stringPreferencesKey("password")
        val EMAIL_KEY = stringPreferencesKey("email")
        val TOKEN_KEY = stringPreferencesKey("token")
    }

    //tahap 3 create user auth
    suspend fun createAndUpdateAccount(username: String, email: String, password: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
            preferences[PASSWORD_KEY] = password
            preferences[EMAIL_KEY] = email
        }
    }


    //tahap 4 read user auth
    private val username: Flow<String?> =
        dataStore.data.map { preferences -> preferences[USERNAME_KEY] }
    private val password: Flow<String?> =
        dataStore.data.map { preferences -> preferences[PASSWORD_KEY] }
    private val email: Flow<String?> =
        dataStore.data.map { preferences -> preferences[EMAIL_KEY] }
    private val token: Flow<String?> =
        dataStore.data.map { preferences -> preferences[TOKEN_KEY] }

    //tahapp 5
    suspend fun loadDataAccount(usernameOrEmailInput: String, passwordInput: String) {
        when {
            (username.first() == usernameOrEmailInput||email.first()==usernameOrEmailInput) && password.first() == passwordInput -> {
                dataStore.edit { preferences ->
                    preferences[TOKEN_KEY] = UUID.randomUUID().toString()
                }
            }else->{
                throw IllegalAccessException("User Empty")
            }
        }
    }

    //tahap 6
    suspend fun loadToken(): String? {
        return token.first()
    }

    suspend fun getAllData(): UserProfileData {
        return UserProfileData(
            username = username.first(),
            email = email.first(),
            password = password.first(),
            token = token.first(),
        )
    }


    //clear token data
    suspend fun clearDataAccount() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }


}
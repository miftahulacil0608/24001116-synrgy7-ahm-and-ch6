package com.example.recyclerviewwithnavigationcomponent.data.model.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfileData(
    val username: String?,
    val email: String?,
    val password: String?,
    val token: String?
) : Parcelable

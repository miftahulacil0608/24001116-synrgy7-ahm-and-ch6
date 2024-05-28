package com.example.recyclerviewwithnavigationcomponent.data.model.dataClass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataItemCollections(
    val id: Int?=null,
    val originalTitle: String?=null,
    val posterPath: String?=null,
) : Parcelable

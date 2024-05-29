package com.example.recyclerviewwithnavigationcomponent.data.model.dataClass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionsMovie(
    val backdropPath: String?="nothing collections",
    val id: Int,
    val name: String,
    val posterPath: String
):Parcelable

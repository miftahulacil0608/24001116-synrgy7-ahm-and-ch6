package com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.detailsresponse.anotherdataclass


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Collections(
    @SerializedName("backdrop_path")
    val backdropPath: String?="nothing collections",
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String
):Parcelable
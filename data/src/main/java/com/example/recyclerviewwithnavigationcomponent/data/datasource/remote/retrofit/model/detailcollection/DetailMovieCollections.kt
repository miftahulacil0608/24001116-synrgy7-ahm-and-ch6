package com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit.model.detailcollection


import com.google.gson.annotations.SerializedName

data class DetailMovieCollections(
    @SerializedName("id")
    val id: Int,
    @SerializedName("parts")
    val parts: List<Part>,
)
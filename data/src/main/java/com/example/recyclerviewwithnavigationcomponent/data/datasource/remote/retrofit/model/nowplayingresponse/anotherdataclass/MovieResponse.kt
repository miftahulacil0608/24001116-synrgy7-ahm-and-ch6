package com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit.model.nowplayingresponse.anotherdataclass

import com.google.gson.annotations.SerializedName



data class MovieResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
)
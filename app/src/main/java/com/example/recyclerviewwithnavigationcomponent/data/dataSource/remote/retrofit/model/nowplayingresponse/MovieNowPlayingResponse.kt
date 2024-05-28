package com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.nowplayingresponse


import com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.nowplayingresponse.anotherdataclass.Dates
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.nowplayingresponse.anotherdataclass.MovieResponse
import com.google.gson.annotations.SerializedName


data class MovieNowPlayingResponse(
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
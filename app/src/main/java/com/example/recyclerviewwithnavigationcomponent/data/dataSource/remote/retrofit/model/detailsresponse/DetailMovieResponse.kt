package com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.detailsresponse


import com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.detailsresponse.anotherdataclass.Collections
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.detailsresponse.anotherdataclass.Genre
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.detailsresponse.anotherdataclass.ProductionCompany
import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val collections: Collections ?= null,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("vote_average")
    val voteAverage: Double,
)
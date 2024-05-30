package com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit.model.nowplayingresponse.anotherdataclass


import com.google.gson.annotations.SerializedName

data class Dates(
    @SerializedName("maximum")
    val maximum: String,
    @SerializedName("minimum")
    val minimum: String
)
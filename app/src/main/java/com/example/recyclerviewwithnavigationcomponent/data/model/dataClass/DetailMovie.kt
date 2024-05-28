package com.example.recyclerviewwithnavigationcomponent.data.model.dataClass

import android.os.Parcelable
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.detailsresponse.anotherdataclass.Collections
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.detailsresponse.anotherdataclass.ProductionCompany
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailMovie(
    val id: Int,
    val backdropPath: String,
    val getCollections: Collections? = null,
    val genres: String,
    val homepage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>,
    val releaseDate: String,
    val runtime: String,
    val voteAverage: Double,
):Parcelable

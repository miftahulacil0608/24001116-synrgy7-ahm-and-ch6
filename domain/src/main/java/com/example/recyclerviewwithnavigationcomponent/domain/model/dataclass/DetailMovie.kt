package com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass


//harusnya menggunakan parcelize
data class DetailMovie(
    val id: Int,
    val backdropPath: String,
    //dibagian ini
    val getCollections: Int? = null,
    val genres: String,
    val homepage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val runtime: String,
    val voteAverage: Double,
)

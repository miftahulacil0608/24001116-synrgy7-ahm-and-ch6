package com.example.recyclerviewwithnavigationcomponent.data.model.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteMovie(
    val id: Int,
    val originalTitle: String,
    val image: String,
    val releaseDate: String,
    val voteAverage: String,
):Parcelable

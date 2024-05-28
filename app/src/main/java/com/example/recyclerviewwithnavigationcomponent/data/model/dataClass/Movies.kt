package com.example.recyclerviewwithnavigationcomponent.data.model.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movies(
    val id: Int,
    val originalTitle: String,
    val image: String,
    val releaseDate: String,
    val voteAverage: Double,
): Parcelable

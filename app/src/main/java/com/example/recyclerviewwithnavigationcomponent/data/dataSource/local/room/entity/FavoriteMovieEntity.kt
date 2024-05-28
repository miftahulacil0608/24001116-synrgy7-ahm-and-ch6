package com.example.recyclerviewwithnavigationcomponent.data.dataSource.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteMovieEntity")
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo("posterImage")
    val posterImage: String,
    @ColumnInfo(name = "titleMovie")
    val titleMovie: String,
    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,
    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double,
)

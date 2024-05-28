package com.example.recyclerviewwithnavigationcomponent.data.repository.movie

import androidx.lifecycle.LiveData
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.Movies

interface LocalMovieDataSource {
    suspend fun insertFavoriteMovie(movie: Movies)
    suspend fun deleteFavoriteMovie(id:Int)
    suspend fun getAllFavoriteMovie(): LiveData<List<Movies>>
    suspend fun checkFavoriteMovie(id:Int):Boolean
}
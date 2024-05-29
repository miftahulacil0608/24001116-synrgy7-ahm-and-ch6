package com.example.recyclerviewwithnavigationcomponent.data.repository.movie

import androidx.lifecycle.LiveData
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.Movies
import kotlinx.coroutines.flow.Flow

interface LocalMovieDataSource {
    suspend fun insertFavoriteMovie(movie: Movies)
    suspend fun deleteFavoriteMovie(id:Int)
    suspend fun getAllFavoriteMovie(): List<Movies>
    suspend fun checkFavoriteMovie(id:Int):Boolean
    suspend fun getAllFavoriteMovies():Flow<List<Movies>>
}
package com.example.recyclerviewwithnavigationcomponent.domain.repository

import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DetailMovie
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.Movies
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    //movie from TMDB API
    //solved
    suspend fun getAllDataMoviesNowPlaying():List<Movies>
    suspend fun setDetailMovie(movieId: Int): DetailMovie?
    suspend fun getDetailCollections(collectionId: Int):List<DataItemCollections>


    //add to favorite
    suspend fun insertFavoriteMovie(movie:Movies)
    //delete from favorite
    suspend fun deleteFavoriteMovie(id:Int)
    //solved get all favorite movie
    suspend fun getAllFavoriteMovie():List<Movies>
    //check data exist or not
    suspend fun checkFavoriteMovie(id:Int):Boolean

    suspend fun getAllFavoriteMovies(): Flow<List<Movies>>

}
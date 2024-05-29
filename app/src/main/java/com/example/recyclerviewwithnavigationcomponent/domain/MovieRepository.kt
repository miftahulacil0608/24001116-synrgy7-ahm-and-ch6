package com.example.recyclerviewwithnavigationcomponent.domain

import androidx.lifecycle.LiveData
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.local.room.entity.FavoriteMovieEntity
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.DetailMovie
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.Movies

interface MovieRepository {
    //movie from TMDB API
    suspend fun getAllDataMoviesNowPlaying():LiveData<List<Movies>>
    suspend fun setDetailMovie(movieId: Int): DetailMovie?
    suspend fun getDetailCollections(collectionId: Int):List<DataItemCollections>


    //add to favorite
    suspend fun insertFavoriteMovie(movie:Movies)
    //delete from favorite
    suspend fun deleteFavoriteMovie(id:Int)
    //read from favorite
    suspend fun getAllFavoriteMovie():LiveData<List<Movies>>
    //check data exist or not
    suspend fun checkFavoriteMovie(id:Int):Boolean

}
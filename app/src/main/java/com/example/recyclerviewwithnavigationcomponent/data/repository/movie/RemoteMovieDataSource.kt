package com.example.recyclerviewwithnavigationcomponent.data.repository.movie

import androidx.lifecycle.LiveData
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DetailMovie
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.Movies

interface RemoteMovieDataSource {


    suspend fun getAllDataMovieNowPlaying(): List<Movies>
    suspend fun setDetailMovie(movieId: Int): DetailMovie
    suspend fun getDetailCollections(collectionId:Int): List<DataItemCollections>
}
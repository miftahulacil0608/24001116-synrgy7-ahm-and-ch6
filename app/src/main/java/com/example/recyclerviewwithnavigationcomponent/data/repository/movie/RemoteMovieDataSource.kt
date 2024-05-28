package com.example.recyclerviewwithnavigationcomponent.data.repository.movie

import androidx.lifecycle.LiveData
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.DetailMovie
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.Movies

interface RemoteMovieDataSource {


    suspend fun getAllDataMovieNowPlaying(): LiveData<List<Movies>>
    suspend fun setDetailMovie(movieId: Int): DetailMovie
    suspend fun getDetailCollections(collectionId:Int): List<DataItemCollections>
}
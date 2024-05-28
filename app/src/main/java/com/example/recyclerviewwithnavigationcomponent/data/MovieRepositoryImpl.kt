package com.example.recyclerviewwithnavigationcomponent.data

import androidx.lifecycle.LiveData
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.local.room.entity.FavoriteMovieEntity
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.DetailMovie
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.Movies
import com.example.recyclerviewwithnavigationcomponent.data.repository.movie.LocalMovieDataSource
import com.example.recyclerviewwithnavigationcomponent.data.repository.movie.RemoteMovieDataSource
import com.example.recyclerviewwithnavigationcomponent.domain.MovieRepository

class MovieRepositoryImpl(
    private val remoteMovieDataSource: RemoteMovieDataSource,
    private val localMovieDataSource: LocalMovieDataSource
) : MovieRepository {

    override suspend fun getAllDataMoviesNowPlaying(): LiveData<List<Movies>> {
        return remoteMovieDataSource.getAllDataMovieNowPlaying()
    }

    override suspend fun setDetailMovie(movieId: Int): DetailMovie {
        return remoteMovieDataSource.setDetailMovie(movieId)
    }

    override suspend fun getDetailCollections(collectionId: Int): List<DataItemCollections> {
        return remoteMovieDataSource.getDetailCollections(collectionId)
    }

    override suspend fun insertFavoriteMovie(movie: Movies) {
        localMovieDataSource.insertFavoriteMovie(movie)
    }

    override suspend fun deleteFavoriteMovie(id: Int) {
        localMovieDataSource.deleteFavoriteMovie(id)
    }

    override suspend fun getAllFavoriteMovie(): LiveData<List<Movies>> {
        return localMovieDataSource.getAllFavoriteMovie()
    }

    override suspend fun checkFavoriteMovie(id: Int): Boolean {
        return localMovieDataSource.checkFavoriteMovie(id)
    }


}
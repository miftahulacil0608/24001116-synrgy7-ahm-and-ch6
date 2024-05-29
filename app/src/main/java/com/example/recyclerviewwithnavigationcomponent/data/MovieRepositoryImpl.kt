package com.example.recyclerviewwithnavigationcomponent.data

import androidx.lifecycle.LiveData
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DetailMovie
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.Movies
import com.example.recyclerviewwithnavigationcomponent.data.repository.movie.LocalMovieDataSource
import com.example.recyclerviewwithnavigationcomponent.data.repository.movie.RemoteMovieDataSource
import com.example.recyclerviewwithnavigationcomponent.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val remoteMovieDataSource: RemoteMovieDataSource,
    private val localMovieDataSource: LocalMovieDataSource
) : MovieRepository {

    override suspend fun getAllDataMoviesNowPlaying(): List<Movies> {
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

    override suspend fun getAllFavoriteMovie(): List<Movies> {
        return localMovieDataSource.getAllFavoriteMovie()
    }

    override suspend fun checkFavoriteMovie(id: Int): Boolean {
        return localMovieDataSource.checkFavoriteMovie(id)
    }

    override suspend fun getAllFavoriteMovies(): Flow<List<Movies>> {
        return localMovieDataSource.getAllFavoriteMovies()
    }


}
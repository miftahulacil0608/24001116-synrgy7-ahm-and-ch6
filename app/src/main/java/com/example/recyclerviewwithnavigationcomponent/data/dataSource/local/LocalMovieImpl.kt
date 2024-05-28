package com.example.recyclerviewwithnavigationcomponent.data.dataSource.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.local.room.dao.FavoriteMovieDao
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.Movies
import com.example.recyclerviewwithnavigationcomponent.data.model.toFavoriteMovieEntity
import com.example.recyclerviewwithnavigationcomponent.data.model.toMovies
import com.example.recyclerviewwithnavigationcomponent.data.repository.movie.LocalMovieDataSource

class LocalMovieImpl(private val favoriteDao: FavoriteMovieDao) : LocalMovieDataSource {
    override suspend fun insertFavoriteMovie(movie: Movies) {
        favoriteDao.insertMovieToFavorite(movie.toFavoriteMovieEntity())
    }

    override suspend fun deleteFavoriteMovie(id: Int) {
        favoriteDao.deleteMovieFromFavorite(id)
    }

    override suspend fun getAllFavoriteMovie(): LiveData<List<Movies>> {
        val resultData = favoriteDao.getAllFavoriteMovie().map {
            it.map { movie ->
                movie.toMovies()
            }
        }
        return resultData
    }

    override suspend fun checkFavoriteMovie(id: Int): Boolean {
        return favoriteDao.isExistsMovieFavorite(id)
    }

}
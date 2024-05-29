package com.example.recyclerviewwithnavigationcomponent.data.dataSource.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.local.room.dao.FavoriteMovieDao
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.Movies
import com.example.recyclerviewwithnavigationcomponent.data.model.toFavoriteMovieEntity
import com.example.recyclerviewwithnavigationcomponent.data.model.toMovies
import com.example.recyclerviewwithnavigationcomponent.data.repository.movie.LocalMovieDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalMovieImpl(private val favoriteDao: FavoriteMovieDao) : LocalMovieDataSource {
    override suspend fun insertFavoriteMovie(movie: Movies) {
        favoriteDao.insertMovieToFavorite(movie.toFavoriteMovieEntity())
    }

    override suspend fun deleteFavoriteMovie(id: Int) {
        favoriteDao.deleteMovieFromFavorite(id)
    }

    override suspend fun getAllFavoriteMovie(): List<Movies> {
        val resultData = favoriteDao.getAllFavoriteMovie().map {
           it.toMovies()
        }
        return resultData
    }

    override suspend fun checkFavoriteMovie(id: Int): Boolean {
        return favoriteDao.isExistsMovieFavorite(id)
    }

    override suspend fun getAllFavoriteMovies(): Flow<List<Movies>> {
        return favoriteDao.getAllFavoriteMovies().map {
            it.map {movies->
                movies.toMovies()
            }
        }
    }

}
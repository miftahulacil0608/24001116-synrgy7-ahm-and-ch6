package com.example.recyclerviewwithnavigationcomponent.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.DetailMovie
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.Movies
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.UserProfileData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UseCase(
    private val movieRepository: MovieRepository,
    private val loginRepository: LoginRepository,
) {
    //Data Movie for Recyclerview
    fun getListMovieNowPlaying(): LiveData<List<Movies>> = liveData {
        try {
            val listDataMovie = movieRepository.getAllDataMoviesNowPlaying()
            emitSource(listDataMovie)
        } catch (throwable: Throwable) {
            throw IllegalAccessException(throwable.message)
        }
    }

    //Detail Movie
    suspend fun setDetailMovie(movieId: Int): DetailMovie? {
        return movieRepository.setDetailMovie(movieId)
    }

    //Collections
    suspend fun getDetailCollections(collectionId: Int): List<DataItemCollections> {
        return when (collectionId) {
            1 -> {
                withContext(Dispatchers.IO) {
                    listOf(
                        DataItemCollections(null, null, null)
                    )
                }
            }

            else -> {
                movieRepository.getDetailCollections(collectionId)
            }
        }

    }

    //Authentication logic
    suspend fun getAllDataUserProfile(): UserProfileData {
        return loginRepository.getAllDataUserProfile()
    }

    suspend fun updateDataUserProfile(username: String, email: String, password: String) {
        loginRepository.updateUserProfile(username, email, password)
    }

    suspend fun clearDataAuth() {
        loginRepository.clearDataAccount()
    }

    //Local Database
    suspend fun checkMovieFavorite(id: Int): Boolean {
         return movieRepository.checkFavoriteMovie(id)
    }

    suspend fun insertDataMovieFavorite(movies: Movies) {
        movieRepository.insertFavoriteMovie(movies)
    }

    suspend fun deleteFromFavoriteMovie(id: Int) {
        movieRepository.deleteFavoriteMovie(id)
    }

    fun getAllDataFavoriteMovie(): LiveData<List<Movies>> = liveData {
        try {
            //kalo udh bisa dependency/sealead class bisa gunakan emit() untuk memasukkan state yang terjadi
            val listDataMovie = movieRepository.getAllFavoriteMovie()
            emitSource(listDataMovie)
        } catch (throwable: Throwable) {
            throw IllegalAccessException(throwable.message)
        }
    }

}
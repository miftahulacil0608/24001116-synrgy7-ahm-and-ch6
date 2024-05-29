package com.example.recyclerviewwithnavigationcomponent.domain.usecase

import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DetailMovie
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.Movies
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.UserProfileData
import com.example.recyclerviewwithnavigationcomponent.domain.repository.LoginRepository
import com.example.recyclerviewwithnavigationcomponent.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow


class UseCase(
    private val movieRepository: MovieRepository,
    private val loginRepository: LoginRepository,
) {
    //Data Movie for Recyclerview
    /*fun getListMovieNowPlaying(): LiveData<List<Movies>> = liveData {
        try {
            val listDataMovie = movieRepository.getAllDataMoviesNowPlaying()
            emitSource(listDataMovie)
        } catch (throwable: Throwable) {
            throw IllegalAccessException(throwable.message)
        }
    }*/

    suspend fun getListMovieNowPlaying(): List<Movies> {
        return movieRepository.getAllDataMoviesNowPlaying()
    }

    //Detail Movie
    suspend fun setDetailMovie(movieId: Int): DetailMovie? {
        return movieRepository.setDetailMovie(movieId)
    }

    //Collections
    suspend fun getDetailCollections(collectionId: Int): List<DataItemCollections> {
        return movieRepository.getDetailCollections(collectionId)
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

//edit liveData dijadikan sebuah list saja
    /*fun getAllDataFavoriteMovie(): LiveData<List<Movies>> = liveData {
        try {
            //kalo udh bisa dependency/sealead class bisa gunakan emit() untuk memasukkan state yang terjadi
            val listDataMovie = movieRepository.getAllFavoriteMovie()
            emitSource(listDataMovie)
        } catch (throwable: Throwable) {
            throw IllegalAccessException(throwable.message)
        }
    }*/

    suspend fun getAllDataFavoriteMovie(): List<Movies> {
        return movieRepository.getAllFavoriteMovie()
    }

    suspend fun getAllDataFavoriteMovies():Flow<List<Movies>>{
        return movieRepository.getAllFavoriteMovies()
    }

}
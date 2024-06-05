package com.example.recyclerviewwithnavigationcomponent.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DetailMovie
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.Movies
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.UserProfileData
import com.example.recyclerviewwithnavigationcomponent.domain.usecase.UseCase
import kotlinx.coroutines.launch

class SharedViewModel(
    private val useCase: UseCase
) : ViewModel() {

    private val _isDataMovieError: MutableLiveData<Throwable> = MutableLiveData()
    val isDataMovieError: LiveData<Throwable> = _isDataMovieError


    //data film
    val dataMovie: LiveData<List<Movies>> = liveData {
        try {
            emit(useCase.getListMovieNowPlaying())
        } catch (throwable: Throwable) {
            _isDataMovieError.value = throwable
            throw IllegalAccessException(throwable.message)
        }
    }

    private val _dataCollections: MutableLiveData<List<DataItemCollections>> = MutableLiveData()
    val dataCollections: LiveData<List<DataItemCollections>> = _dataCollections

    private fun clearDataCollections() {
        _dataCollections.value = emptyList() // Mengosongkan data koleksi
    }

    private fun updateDataCollections(newData: List<DataItemCollections>) {
        _dataCollections.value = newData // Memperbarui data koleksi dengan data baru
    }


    //dataDetailMovie
    private val _dataDetailMovie: MutableLiveData<DetailMovie> = MutableLiveData()
    val dataDetailMovie: LiveData<DetailMovie> = _dataDetailMovie

    private val _isDataDetailExist: MutableLiveData<Boolean> = MutableLiveData()
    val isDataDetailExist: LiveData<Boolean> = _isDataDetailExist

    private val _isDataDetailError: MutableLiveData<Throwable> = MutableLiveData()
    val isDataDetailError: LiveData<Throwable> = _isDataDetailError

    fun setDetailMovie(movieId: Int) {
        try {
            viewModelScope.launch {
                _isDataDetailExist.value = false
                _dataDetailMovie.value = useCase.setDetailMovie(movieId)
                _isDataDetailExist.value = true
            }
        } catch (throwable: Throwable) {
            _isDataDetailError.value = throwable
            throw IllegalAccessException(throwable.message)
        }
    }

    fun setDetailCollection(collectionId: Int) {
        try {
            viewModelScope.launch {
                //clear list dulu
                clearDataCollections()
                // baru diisi ulang
                when (collectionId) {
                    1 -> updateDataCollections(
                        listOf(
                            DataItemCollections(null, null, null)
                        )
                    )
                    else -> updateDataCollections(useCase.getDetailCollections(collectionId))
                }
            }

        } catch (throwable: Throwable) {
            throw IllegalAccessException(throwable.message)
        }
    }

    //Local Database

    private val _isFavoriteMovieExists = MutableLiveData<Boolean>()
    val isFavoriteMovieExists: LiveData<Boolean> = _isFavoriteMovieExists

    fun checkFavoriteMovie(id: Int) {
        viewModelScope.launch {
            _isFavoriteMovieExists.value = useCase.checkMovieFavorite(id)
        }
    }

    fun insertFavoriteMovie(movie: Movies) {
        viewModelScope.launch {
            useCase.insertDataMovieFavorite(movie)
            checkFavoriteMovie(movie.id)
        }
    }

    fun deleteFavoriteMovie(id: Int) {
        viewModelScope.launch {
            useCase.deleteFromFavoriteMovie(id)
            checkFavoriteMovie(id)

        }
    }


    private val _isDataFavoriteMovieError: MutableLiveData<Throwable> = MutableLiveData()
    val isDataFavoriteMovieError: LiveData<Throwable> = _isDataFavoriteMovieError

    val dataFavoriteMovie: LiveData<List<Movies>> = liveData {
        try {
            val flow = useCase.getAllDataFavoriteMovies().asLiveData()
            emitSource(flow)

        } catch (throwable: Throwable) {
            _isDataFavoriteMovieError.value = throwable
            throw IllegalAccessException(throwable.message)
        }
    }


}
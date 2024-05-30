package com.example.recyclerviewwithnavigationcomponent.ui.mvvm

import android.content.Context
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.savedstate.SavedStateRegistryOwner
import com.example.recyclerviewwithnavigationcomponent.data.datasource.local.LocalLoginImpl
import com.example.recyclerviewwithnavigationcomponent.data.datasource.local.LocalMovieImpl

import com.example.recyclerviewwithnavigationcomponent.data.datasource.local.room.AppDatabase
import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.RemoteLoginImpl
import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.RemoteMovieImpl
import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit.MovieService
import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit.model.ApiConfig

import com.example.recyclerviewwithnavigationcomponent.data.model.AuthPreferences
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DetailMovie
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.Movies
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.UserProfileData
import com.example.recyclerviewwithnavigationcomponent.data.model.dataStore
import com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.LocalLoginDataSource
import com.example.recyclerviewwithnavigationcomponent.data.repository.movie.RemoteMovieDataSource
import com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.RemoteLoginDataSource
import com.example.recyclerviewwithnavigationcomponent.data.repository.movie.LocalMovieDataSource
import com.example.recyclerviewwithnavigationcomponent.domain.repository.MovieRepository
import com.example.recyclerviewwithnavigationcomponent.domain.repository.LoginRepository
import com.example.recyclerviewwithnavigationcomponent.domain.usecase.UseCase
import kotlinx.coroutines.launch

class SharedViewModel(
    private val useCase: UseCase
) : ViewModel() {
    companion object {
        fun provideFactory(
            owner: SavedStateRegistryOwner,
            context: Context
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, null) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {

                    val appDatabase: AppDatabase = Room.databaseBuilder(
                        name = "appDatabase",
                        klass = AppDatabase::class.java,
                        context = context.applicationContext,
                    ).build()

                    val movieService = ApiConfig.getTMBDService().create(MovieService::class.java)

                    val remoteMovieDataSource: RemoteMovieDataSource = RemoteMovieImpl(
                            movieService
                        )
                    val localMovieDataSource: LocalMovieDataSource =
                   LocalMovieImpl(
                            appDatabase.favoriteMovieDao()
                        )

                    val authPreferences =
                        AuthPreferences(
                            context.dataStore
                        )

                    val remoteLoginDataSource: RemoteLoginDataSource =
                        RemoteLoginImpl(
                            authPreferences
                        )
                    val localLoginDataSource: LocalLoginDataSource =
                        LocalLoginImpl(
                            authPreferences
                        )

                    val movieRepository: MovieRepository =
                        com.example.recyclerviewwithnavigationcomponent.data.MovieRepositoryImpl(
                            remoteMovieDataSource = remoteMovieDataSource,
                            localMovieDataSource = localMovieDataSource,
                        )
                    val loginRepository: LoginRepository =
                        com.example.recyclerviewwithnavigationcomponent.data.LoginDataSource(
                            remoteLoginDataSource = remoteLoginDataSource,
                            localLoginDataSource = localLoginDataSource,
                        )
                    val useCase = UseCase(
                        movieRepository = movieRepository,
                        loginRepository = loginRepository,
                    )
                    //LoginRepository Instance
                    return SharedViewModel(
                        useCase = useCase,
                    ) as T

                }
            }
    }


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

    //isUpdateUserProfile
    private val _isUpdateUserProfile = MutableLiveData<Boolean>()
    val isUpdateUserProfile: LiveData<Boolean> = _isUpdateUserProfile

    private val _errorUpdateUserProfile = MutableLiveData<Throwable>()
    val errorUpdateUserProfile: LiveData<Throwable> = _errorUpdateUserProfile

    fun updateAccountUserProfile(username: String, email: String, password: String) {
        try {
            viewModelScope.launch {
                _isUpdateUserProfile.value = false
                useCase.updateDataUserProfile(username, email, password)
                _isUpdateUserProfile.value = true
                refreshUserProfile()

            }
        } catch (throwable: Throwable) {
            _errorUpdateUserProfile.value = throwable
            _isUpdateUserProfile.value = false
            throw IllegalAccessException(throwable.message)
        }


    }

    private val _userProfile = MutableLiveData<UserProfileData>()
    val userProfile: LiveData<UserProfileData> = _userProfile

    fun refreshUserProfile() {
        try {
            viewModelScope.launch {
                _isUpdateUserProfile.value = false
                _userProfile.value = useCase.getAllDataUserProfile()
                _isUpdateUserProfile.value = true
            }
        } catch (throwable: Throwable) {

            throw IllegalAccessException(throwable.message)
        }
    }


    private var _successLogout = MutableLiveData<Boolean>()
    val successLogout: LiveData<Boolean> = _successLogout

    fun logout() {
        viewModelScope.launch {
            _successLogout.value = false
            useCase.clearDataAuth()
            _successLogout.value = true
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
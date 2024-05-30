package com.example.recyclerviewwithnavigationcomponent.di

import android.content.Context
import androidx.room.Room
import com.example.recyclerviewwithnavigationcomponent.data.AuthenticationDataSource
import com.example.recyclerviewwithnavigationcomponent.data.MovieRepositoryImpl
import com.example.recyclerviewwithnavigationcomponent.data.datasource.local.LocalAuthenticationImpl
import com.example.recyclerviewwithnavigationcomponent.data.datasource.local.LocalMovieImpl
import com.example.recyclerviewwithnavigationcomponent.data.datasource.local.room.AppDatabase
import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.RemoteAuthenticationImpl
import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.RemoteMovieImpl
import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit.MovieService
import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit.model.ApiConfig
import com.example.recyclerviewwithnavigationcomponent.data.model.AuthPreferences
import com.example.recyclerviewwithnavigationcomponent.data.model.dataStore
import com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.LocalAuthenticationDataSource
import com.example.recyclerviewwithnavigationcomponent.data.repository.authentication.RemoteAuthenticationDataSource
import com.example.recyclerviewwithnavigationcomponent.data.repository.movie.LocalMovieDataSource
import com.example.recyclerviewwithnavigationcomponent.data.repository.movie.RemoteMovieDataSource
import com.example.recyclerviewwithnavigationcomponent.domain.repository.AuthenticationRepository
import com.example.recyclerviewwithnavigationcomponent.domain.repository.MovieRepository
import com.example.recyclerviewwithnavigationcomponent.domain.usecase.UseCase

class Module(context: Context) {

    //dataStore
    private val authPreferences: AuthPreferences by lazy {
        AuthPreferences(context.dataStore)
    }

    //remoteAuthenticationDataSource
    private val remoteAuthenticationDataSource: RemoteAuthenticationDataSource by lazy {
        RemoteAuthenticationImpl(authPreferences)
    }

    //localAuthenticationDataSource
    private val localAuthenticationDataSource: LocalAuthenticationDataSource by lazy {
        LocalAuthenticationImpl(dataStore = authPreferences)
    }

    //authenticationRepository
    val authenticationRepository: AuthenticationRepository by lazy {
        AuthenticationDataSource(
            remoteAuthenticationDataSource = remoteAuthenticationDataSource,
            localAuthenticationDataSource = localAuthenticationDataSource,
        )
    }

    //database
    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(
            name = "appDatabase",
            klass = AppDatabase::class.java,
            context = context.applicationContext,
        ).build()
    }

    //movieService
    private val movieService:MovieService by lazy {
        ApiConfig.getTMBDService().create(MovieService::class.java)
    }

    //remoteMovieDataSource
    private val remoteMovieDataSource: RemoteMovieDataSource by lazy {
        RemoteMovieImpl(movieService)
    }

    //localMovieDataSource
    private val localMovieDataSource: LocalMovieDataSource by lazy {
        LocalMovieImpl(appDatabase.favoriteMovieDao())
    }

    //movieRepository
    private val movieRepository: MovieRepository by lazy {
        MovieRepositoryImpl(
            remoteMovieDataSource = remoteMovieDataSource,
            localMovieDataSource = localMovieDataSource,
        )
    }

    //useCase
    val useCase: UseCase by lazy {
        UseCase(movieRepository = movieRepository, authenticationRepository = authenticationRepository)
    }
}
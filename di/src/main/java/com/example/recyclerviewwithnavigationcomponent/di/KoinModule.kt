package com.example.recyclerviewwithnavigationcomponent.di

import android.app.Application
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
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val koinModule = module {

    //dataStore
    single<AuthPreferences> {
        AuthPreferences(androidContext().dataStore)
    }

    //remoteAuthenticationDataSource
    single<RemoteAuthenticationDataSource> {
        RemoteAuthenticationImpl(get())
    }


    //localAuthenticationDataSource
    single<LocalAuthenticationDataSource> {
        LocalAuthenticationImpl(dataStore = get())
    }

    //authenticationRepository
    single<AuthenticationRepository> {
        AuthenticationDataSource(
            remoteAuthenticationDataSource = get(),
            localAuthenticationDataSource = get(),
        )
    }

    //database
    single<AppDatabase> {
        Room.databaseBuilder(
            name = "appDatabase",
            klass = AppDatabase::class.java,
            context = androidContext().applicationContext,
        ).build()
    }

    //movieService
    single<MovieService> {
        ApiConfig.getTMBDService().create(MovieService::class.java)
    }

    //remoteMovieDataSource
    single<RemoteMovieDataSource> {
        RemoteMovieImpl(get())
    }

    //localMovieDataSource
    single<LocalMovieDataSource> {
        LocalMovieImpl((get() as AppDatabase).favoriteMovieDao())
    }

    //movieRepository
    single<MovieRepository> {
        MovieRepositoryImpl(
            remoteMovieDataSource = get(),
            localMovieDataSource = get(),
        )
    }

    //useCase
    single<UseCase> {
        UseCase(
            movieRepository = get(),
            authenticationRepository = get()
        )
    }
}
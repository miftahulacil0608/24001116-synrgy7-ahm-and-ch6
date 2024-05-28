package com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model

import com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.MovieService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    const val TMBD_API_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyOTUxMzBjYTUyNjRhNjg0Yjk4ZTZkMGRiMDViMzhmNiIsInN1YiI6IjY2M2EyYTJiY2MyNzdjMDEyMzI0YTg0MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.u9gK_e0zwcB0LMPNuhVZr8kX_1mFBeyDv7gr3rxWevk"
    const val AUTHORIZATION = "authorization: Bearer $TMBD_API_TOKEN"
    const val ACCEPT = "accept: application/json"


    //digunakan untuk ngecek interceptor dari okhttp ke dalam logcat
    private fun provideOkHttpInterceptor():OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    //digunakan untuk retrofit
    fun getTMBDService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(provideOkHttpInterceptor())
            .build()
    }
}
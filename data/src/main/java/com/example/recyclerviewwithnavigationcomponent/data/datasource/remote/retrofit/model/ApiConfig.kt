package com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit.model

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    const val TMBD_API_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyOTUxMzBjYTUyNjRhNjg0Yjk4ZTZkMGRiMDViMzhmNiIsInN1YiI6IjY2M2EyYTJiY2MyNzdjMDEyMzI0YTg0MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.u9gK_e0zwcB0LMPNuhVZr8kX_1mFBeyDv7gr3rxWevk"

    private val interceptor:HttpLoggingInterceptor=HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private fun provideInterceptor() = Interceptor{
        val request = it.request()
        val requestHeaders = request.newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("authorization","Bearer $TMBD_API_TOKEN")
            .build()
        it.proceed(requestHeaders)
    }


    //digunakan untuk ngecek interceptor dari okhttp ke dalam logcat
    private fun provideOkHttpInterceptor(loggingClient: ChuckerInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(provideInterceptor())
            .addInterceptor(loggingClient)
            .connectTimeout(0,TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(0, TimeUnit.SECONDS)
            .build()
    }


    //digunakan untuk retrofit
    fun getTMBDService(loggingClient:ChuckerInterceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(provideOkHttpInterceptor(loggingClient))
            .build()
    }

}
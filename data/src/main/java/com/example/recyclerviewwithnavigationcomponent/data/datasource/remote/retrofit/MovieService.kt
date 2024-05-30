package com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit

import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit.model.ApiConfig
import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit.model.detailcollection.DetailMovieCollections
import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit.model.detailsresponse.DetailMovieResponse
import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit.model.nowplayingresponse.MovieNowPlayingResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    //headersnya masih manual, tapi bisa diubah nantinya
    @GET("movie/now_playing")
    @Headers(ApiConfig.AUTHORIZATION, ApiConfig.ACCEPT, ApiConfig.ACCEPT)
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): MovieNowPlayingResponse

    @GET("movie/{movie_id}")
    @Headers(ApiConfig.AUTHORIZATION, ApiConfig.ACCEPT, ApiConfig.ACCEPT)
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") appendToResponse: String? = null,
        @Query("language") language: String = "en-US"
    ): DetailMovieResponse

    @GET("collection/{collection_id}")
    @Headers(ApiConfig.AUTHORIZATION, ApiConfig.ACCEPT, ApiConfig.ACCEPT)
    suspend fun getCollectionDetail(
     @Path("collection_id") collectionId: Int,
     @Query("language") language: String = "en-US"
    ): DetailMovieCollections


}
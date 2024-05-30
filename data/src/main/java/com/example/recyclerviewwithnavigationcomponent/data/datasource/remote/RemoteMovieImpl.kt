package com.example.recyclerviewwithnavigationcomponent.data.datasource.remote

import com.example.recyclerviewwithnavigationcomponent.data.datasource.remote.retrofit.MovieService
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.DetailMovie
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.Movies
import com.example.recyclerviewwithnavigationcomponent.data.model.toDataItemCollections
import com.example.recyclerviewwithnavigationcomponent.data.model.toDetailMovie
import com.example.recyclerviewwithnavigationcomponent.data.model.toMovies

class RemoteMovieImpl(
    private val movieService: MovieService
) : com.example.recyclerviewwithnavigationcomponent.data.repository.movie.RemoteMovieDataSource {
    override suspend fun getAllDataMovieNowPlaying(): List<Movies> {
        val movieList = movieService.getNowPlayingMovies().results
        return movieList.map {
            it.toMovies()
        }
    }

    override suspend fun setDetailMovie(movieId: Int): DetailMovie {
        val detailMovieResponse = movieService.getMovieDetail(movieId)
        return detailMovieResponse.toDetailMovie()
    }

    override suspend fun getDetailCollections(collectionId: Int): List<DataItemCollections> {
        return movieService.getCollectionDetail(collectionId).parts.map {
            it.toDataItemCollections()
        }
    }
}
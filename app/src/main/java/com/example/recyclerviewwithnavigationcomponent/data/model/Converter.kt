package com.example.recyclerviewwithnavigationcomponent.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.local.room.entity.FavoriteMovieEntity
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.detailcollection.Part
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.detailsresponse.DetailMovieResponse
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.detailsresponse.anotherdataclass.Collections
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.remote.retrofit.model.nowplayingresponse.anotherdataclass.MovieResponse
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.CollectionsMovie
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.DataItemCollections
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.DetailMovie
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.Movies
import com.example.recyclerviewwithnavigationcomponent.data.model.dataClass.UserProfileData

fun MovieResponse.toMovies(): Movies {
    return Movies(
        id = id,
        originalTitle = originalTitle,
        image = "https://image.tmdb.org/t/p/w342/$posterPath",
        releaseDate = releaseDate,
        voteAverage = voteAverage,
    )
}

fun Collections.toCollectionsMovie(): CollectionsMovie {
    return CollectionsMovie(
        id = id,
        posterPath = posterPath,
        name = name,
        backdropPath = backdropPath
    )
}

fun DetailMovieResponse.toDetailMovie(): DetailMovie {
    val genres = genres.joinToString(separator = ", ") {
        it.name
    }
    return DetailMovie(
        id = id,
        backdropPath = "https://image.tmdb.org/t/p/w780/$backdropPath",
        getCollections = collections?.toCollectionsMovie(),
        genres = genres,
        homepage = homepage,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = "https://image.tmdb.org/t/p/w342/$posterPath",
        productionCompanies = productionCompanies,
        releaseDate = releaseDate,
        runtime = "Runtime: $runtime minutes",
        voteAverage = voteAverage,
    )
}

fun Part.toDataItemCollections(): DataItemCollections {
    return DataItemCollections(
        id = id,
        posterPath = "https://image.tmdb.org/t/p/w342/$posterPath",
        originalTitle = originalTitle
    )
}

fun UserProfileData.toLiveData(): LiveData<UserProfileData> {
    return MutableLiveData(
        UserProfileData(
            username = username,
            email = email,
            password = password,
            token = token
        )
    )
}

fun Movies.toFavoriteMovieEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        titleMovie = originalTitle,
        posterImage = image,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}

fun FavoriteMovieEntity.toMovies(): Movies {
    return Movies(
        id = id,
        originalTitle = titleMovie,
        image = posterImage,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}
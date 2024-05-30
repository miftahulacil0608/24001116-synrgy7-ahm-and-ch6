package com.example.recyclerviewwithnavigationcomponent.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recyclerviewwithnavigationcomponent.data.datasource.local.room.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieToFavorite(dataMovie: FavoriteMovieEntity)

    @Query("SELECT * FROM FavoriteMovieEntity")
    suspend fun getAllFavoriteMovie(): List<FavoriteMovieEntity>

    @Query("SELECT * FROM FavoriteMovieEntity")
     fun getAllFavoriteMovies(): Flow<List<FavoriteMovieEntity>>

    @Query("DELETE FROM FavoriteMovieEntity WHERE id = :id")
    suspend fun deleteMovieFromFavorite(id: Int)

    @Query("SELECT EXISTS (SELECT * FROM FavoriteMovieEntity WHERE id = :id)")
    suspend fun isExistsMovieFavorite(id: Int): Boolean
}
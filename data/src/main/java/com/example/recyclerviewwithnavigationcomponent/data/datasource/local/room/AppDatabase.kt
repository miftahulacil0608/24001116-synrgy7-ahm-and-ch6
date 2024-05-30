package com.example.recyclerviewwithnavigationcomponent.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recyclerviewwithnavigationcomponent.data.datasource.local.room.dao.FavoriteMovieDao
import com.example.recyclerviewwithnavigationcomponent.data.datasource.local.room.entity.FavoriteMovieEntity

@Database(entities = [FavoriteMovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
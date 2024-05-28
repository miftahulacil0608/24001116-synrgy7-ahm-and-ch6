package com.example.recyclerviewwithnavigationcomponent.data.dataSource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.local.room.dao.FavoriteMovieDao
import com.example.recyclerviewwithnavigationcomponent.data.dataSource.local.room.entity.FavoriteMovieEntity

@Database(entities = [FavoriteMovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    /*abstract fun leagueDao() : LeagueDao
    abstract fun teamsDao():TeamsDao*/
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
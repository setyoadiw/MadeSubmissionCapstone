package com.setyo.common.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.setyo.common.data.source.local.entity.FavoriteEntity
import com.setyo.common.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}
package com.setyo.common.data.source.local

import com.setyo.common.data.source.local.entity.FavoriteEntity
import com.setyo.common.data.source.local.entity.MovieEntity
import com.setyo.common.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {

    fun getMovie(): Flow<List<MovieEntity>> = movieDao.getMovie()

    fun getFavoriteMovie(): Flow<List<FavoriteEntity>> = movieDao.getFavoriteMovie()

    fun getFavoriteMovieById(id:Int): Flow<List<FavoriteEntity>> = movieDao.getFavoriteMovieById(id)

    suspend fun insertMovie(movie: List<MovieEntity>) = movieDao.insertMovie(movie)

    suspend fun setFavoriteMovie(movie: FavoriteEntity, shouldAdd: Boolean) {
        if (shouldAdd) movieDao.insertFavoriteMovie(movie)
        else movieDao.removeFavoriteMovieById(movie.id)
    }
}
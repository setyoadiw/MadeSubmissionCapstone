package com.setyo.common.data.source.local.room

import androidx.room.*
import com.setyo.common.data.source.local.entity.FavoriteEntity
import com.setyo.common.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM favorite")
    fun getFavoriteMovie(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun getFavoriteMovieById(id: Int): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun removeFavoriteMovieById(id: Int)


}

package com.setyo.common.domain.usecase

import com.setyo.common.data.Resource
import com.setyo.common.domain.model.DetailMovieData
import com.setyo.common.domain.model.MovieData
import com.setyo.common.domain.model.ReviewData
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovie(forceFetch: Boolean): Flow<Resource<List<MovieData>>>
    fun getTopMovie(forceFetch: Boolean): Flow<Resource<List<MovieData>>>
    fun searchMovie(query: String): Flow<Resource<List<MovieData>>>
    fun getDetailMovie(id: String):Flow<Resource<DetailMovieData>>
    fun getReview(id: String):Flow<Resource<List<ReviewData>>>
    fun getFavoriteMovieById(id: Int): Flow<List<MovieData>>
    suspend fun getFavoriteMovie() : Flow<List<MovieData>>
    suspend fun setMovieFavorite(movie: MovieData, newState: Boolean)
}
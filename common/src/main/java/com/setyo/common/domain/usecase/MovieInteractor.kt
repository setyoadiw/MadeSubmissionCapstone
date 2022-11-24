package com.setyo.common.domain.usecase

import com.setyo.common.data.Resource
import com.setyo.common.domain.model.DetailMovieData
import com.setyo.common.domain.model.MovieData
import com.setyo.common.domain.model.ReviewData
import com.setyo.common.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val repository: IMovieRepository) : MovieUseCase {
    override fun getMovie(forceFetch: Boolean): Flow<Resource<List<MovieData>>> =
        repository.getMovie(forceFetch)

    override fun getTopMovie(forceFetch: Boolean): Flow<Resource<List<MovieData>>> =
        repository.getMovie(forceFetch)

    override fun searchMovie(query: String): Flow<Resource<List<MovieData>>> =
        repository.searchMovie(query)

    override fun getDetailMovie(id: String): Flow<Resource<DetailMovieData>> =
        repository.getDetailMovie(id)

    override fun getReview(id: String): Flow<Resource<List<ReviewData>>> =
        repository.getReview(id)

    override fun getFavoriteMovieById(id: Int): Flow<List<MovieData>> =
        repository.getFavoriteMovieById(id)

    override suspend fun getFavoriteMovie(): Flow<List<MovieData>> =
        repository.getFavoriteMovie()

    override suspend fun setMovieFavorite(movie: MovieData, newState: Boolean) {
        repository.setMovieFavorite(movie, newState)
    }

}
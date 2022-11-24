package com.setyo.common.data.repository

import com.setyo.common.data.Resource
import com.setyo.common.data.network.NetworkBoundResource
import com.setyo.common.data.source.local.LocalDataSource
import com.setyo.common.data.source.remote.RemoteDataSource
import com.setyo.common.data.source.remote.network.ApiResponse
import com.setyo.common.data.source.remote.response.DetailMovieResponse
import com.setyo.common.data.source.remote.response.ResultsPopularMovie
import com.setyo.common.data.source.remote.response.ResultsReview
import com.setyo.common.data.source.remote.response.ResultsSearchMovie
import com.setyo.common.domain.model.DetailMovieData
import com.setyo.common.domain.model.MovieData
import com.setyo.common.domain.model.ReviewData
import com.setyo.common.domain.repository.IMovieRepository
import com.setyo.common.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MovieRepository(private val remoteDataSource: RemoteDataSource,
                      private val localDataSource: LocalDataSource) : IMovieRepository {

    override fun getMovie(forceFetch: Boolean): Flow<Resource<List<MovieData>>> =
        object : NetworkBoundResource<List<MovieData>, List<ResultsPopularMovie>>() {
            override fun loadFromDB(): Flow<List<MovieData>> =
                localDataSource.getMovie().map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<MovieData>?): Boolean = forceFetch

            override suspend fun createCall(): ApiResponse<List<ResultsPopularMovie>> =
                remoteDataSource.getPopularMovie()

            override suspend fun saveCallResult(data: List<ResultsPopularMovie>) {
                localDataSource.insertMovie(DataMapper.mapMovieResponsesToEntities(data))
            }

            override suspend fun callResult(response: List<ResultsPopularMovie>): List<MovieData> =
                DataMapper.mapMovieResponsesToDomain(response)

        }.asFlow()

    override fun getTopMovie(forceFetch: Boolean): Flow<Resource<List<MovieData>>> =
        object : NetworkBoundResource<List<MovieData>, List<ResultsPopularMovie>>() {
            override fun loadFromDB(): Flow<List<MovieData>> =
                localDataSource.getMovie().map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<MovieData>?): Boolean = forceFetch

            override suspend fun createCall(): ApiResponse<List<ResultsPopularMovie>> =
                remoteDataSource.getTopMovie()

            override suspend fun saveCallResult(data: List<ResultsPopularMovie>) {
                localDataSource.insertMovie(DataMapper.mapMovieResponsesToEntities(data))
            }

            override suspend fun callResult(response: List<ResultsPopularMovie>): List<MovieData> =
                DataMapper.mapMovieResponsesToDomain(response)

        }.asFlow()

    override fun searchMovie(query: String): Flow<Resource<List<MovieData>>> =
        object : NetworkBoundResource<List<MovieData>, List<ResultsSearchMovie>>() {
            override fun loadFromDB(): Flow<List<MovieData>> =
                localDataSource.getMovie().map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<MovieData>?): Boolean = true

            override suspend fun createCall(): ApiResponse<List<ResultsSearchMovie>> =
                remoteDataSource.searchMovie(query)

            override suspend fun saveCallResult(data: List<ResultsSearchMovie>) {
                // not saving to db
            }

            override suspend fun callResult(response: List<ResultsSearchMovie>): List<MovieData> =
                DataMapper.mapSearchMovieResponsesToDomain(response)

        }.asFlow()


    override fun getDetailMovie(id: String): Flow<Resource<DetailMovieData>> =
        object : NetworkBoundResource<DetailMovieData, DetailMovieResponse>() {
            override fun loadFromDB(): Flow<DetailMovieData> =
                flowOf(DetailMovieData())

            override fun shouldFetch(data: DetailMovieData?): Boolean  = true

            override suspend fun createCall(): ApiResponse<DetailMovieResponse> =
                remoteDataSource.getDetailMovie(id)

            override suspend fun saveCallResult(data: DetailMovieResponse) {
                // not saving to db
            }

            override suspend fun callResult(response: DetailMovieResponse): DetailMovieData =
                DataMapper.mapDetailResponseToDomain(response)

        }.asFlow()

    override fun getReview(id: String): Flow<Resource<List<ReviewData>>> =
        object : NetworkBoundResource<List<ReviewData>, List<ResultsReview>>() {
            override fun loadFromDB(): Flow<List<ReviewData>> =
                flowOf(listOf(ReviewData()))

            override suspend fun createCall(): ApiResponse<List<ResultsReview>> =
                remoteDataSource.getReviewMovie(id)

            override fun shouldFetch(data: List<ReviewData>?): Boolean  = true

            override suspend fun saveCallResult(data: List<ResultsReview>) {
                // not saving to db
            }

            override suspend fun callResult(response: List<ResultsReview>): List<ReviewData> =
                DataMapper.mapReviewResponseToDomain(response)


        }.asFlow()

    override fun getFavoriteMovieById(id: Int): Flow<List<MovieData>> =
        localDataSource.getFavoriteMovieById(id).map {
            DataMapper.mapFavoriteMovieEntitiesToDomain(it)
        }


    override suspend fun getFavoriteMovie(): Flow<List<MovieData>> =
        localDataSource.getFavoriteMovie().map {
            DataMapper.mapFavoriteMovieEntitiesToDomain(it)
        }

    override suspend fun setMovieFavorite(movie: MovieData, newState: Boolean) =
        localDataSource.setFavoriteMovie(DataMapper.mapFavoriteMovieDomainToEntities(movie), newState)


}
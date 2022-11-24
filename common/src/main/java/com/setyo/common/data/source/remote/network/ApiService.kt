package com.setyo.common.data.source.remote.network

import com.setyo.common.data.source.remote.response.DetailMovieResponse
import com.setyo.common.data.source.remote.response.PopularMovieResponse
import com.setyo.common.data.source.remote.response.ReviewResponse
import com.setyo.common.data.source.remote.response.SearchMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/3/trending/all/day")
    suspend fun getPopularMovie(
    ): PopularMovieResponse


    @GET("/3/movie/top_rated")
    suspend fun getTopMovie(
    ): PopularMovieResponse


    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("query") query: String
    ): SearchMovieResponse


    @GET("/3/movie/{id}")
    suspend fun getDetailMovie(
        @Path("id") id: String
    ): DetailMovieResponse

    @GET("/3/movie/{id}/reviews")
    suspend fun getReview(
        @Path("id") id: String
    ): ReviewResponse
}

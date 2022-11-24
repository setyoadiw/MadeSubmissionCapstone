package com.setyo.common.data.source.remote

import com.setyo.common.data.source.remote.network.ApiResponse
import com.setyo.common.data.source.remote.network.ApiService
import com.setyo.common.data.source.remote.response.DetailMovieResponse
import com.setyo.common.data.source.remote.response.ResultsPopularMovie
import com.setyo.common.data.source.remote.response.ResultsReview
import com.setyo.common.data.source.remote.response.ResultsSearchMovie

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getPopularMovie(): ApiResponse<List<ResultsPopularMovie>> {
        return try {
            val response = apiService.getPopularMovie()
            ApiResponse.Success(response.results)
        } catch (e: Exception) {
            ApiResponse.Error(e.toString())
        }
    }

    suspend fun getTopMovie(): ApiResponse<List<ResultsPopularMovie>> {
        return try {
            val response = apiService.getTopMovie()
            ApiResponse.Success(response.results)
        } catch (e: Exception) {
            ApiResponse.Error(e.toString())
        }
    }

    suspend fun searchMovie(query : String): ApiResponse<List<ResultsSearchMovie>> {
        return try {
            val response = apiService.searchMovie(query)
            ApiResponse.Success(response.results)
        } catch (e: Exception) {
            ApiResponse.Error(e.toString())
        }
    }

    suspend fun getDetailMovie(id: String): ApiResponse<DetailMovieResponse> {
        return try {
            val response = apiService.getDetailMovie(id)
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e.toString())
        }
    }

    suspend fun getReviewMovie(id : String): ApiResponse<List<ResultsReview>> {
        return try {
            val response = apiService.getReview(id)
            ApiResponse.Success(response.results)
        } catch (e: Exception) {
            ApiResponse.Error(e.toString())
        }
    }


}


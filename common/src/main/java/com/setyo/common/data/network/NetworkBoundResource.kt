package com.setyo.common.data.network

import com.setyo.common.data.Resource
import com.setyo.common.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val apiResponse = createCall()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emit(Resource.Success(callResult(apiResponse.data)))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
            }
        } else {
            emitAll(loadFromDB().map {Resource.Success(it)})
        }
    }

    protected abstract suspend fun callResult(response: RequestType): ResultType

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): ApiResponse<RequestType>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}
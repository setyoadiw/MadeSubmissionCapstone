package com.setyo.common.data.source.remote.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val url = req.url.newBuilder().apply {
            addQueryParameter("api_key", "8f0079291f0ab41d8e4d01294fc0c2ea")
        }.build()

        val requestBuilder = req.newBuilder()
            .url(url).build()

        return chain.proceed(requestBuilder)
    }
}

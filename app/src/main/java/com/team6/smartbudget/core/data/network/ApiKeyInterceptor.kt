package com.team6.smartbudget.core.data.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        println(apiKey)
        println(url)
        return chain.proceed(
            request
                .newBuilder()
                .url(url)
                .build(),
        )
    }
}

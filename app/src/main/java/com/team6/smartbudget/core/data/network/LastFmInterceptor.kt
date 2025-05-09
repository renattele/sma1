package com.team6.smartbudget.core.data.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LastFmInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalUrl = chain.request().url
        val url = originalUrl.newBuilder()
            .addQueryParameter("format", "json")
            .build()
        return chain.proceed(
            chain.request()
                .newBuilder()
                .url(url)
                .build(),
        )
    }
}

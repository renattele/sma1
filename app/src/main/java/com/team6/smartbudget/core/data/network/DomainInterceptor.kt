package com.team6.smartbudget.core.data.network

import com.team6.smartbudget.sma1.BuildConfig
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class DomainInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = (BuildConfig.API_BASE_URL + request.url.encodedPath.trim('/'))
            .toHttpUrlOrNull() ?: request.url
        return chain.proceed(
            request
                .newBuilder()
                .url(newUrl)
                .build(),
        )
    }
}

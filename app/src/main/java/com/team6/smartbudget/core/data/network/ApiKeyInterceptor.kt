package com.team6.smartbudget.core.data.network

import com.team6.smartbudget.core.domain.repository.ApiKeyRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

class ApiKeyInterceptor @Inject constructor(
    private val apiKeyRepository: Provider<ApiKeyRepository>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // runBlocking because intercept is a synchronous method
        val apiKey = runBlocking { apiKeyRepository.get().apiKey().first() }

        val url = if (apiKey != null) {
            request.url.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()
        } else {
            request.url
        }

        return chain.proceed(
            request.newBuilder().url(url).build(),
        )
    }
}

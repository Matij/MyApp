package com.martafode.lib.rest

import java.io.IOException
import javax.inject.Inject
import javax.inject.Provider
import okhttp3.Interceptor
import okhttp3.Response

private const val AUTHORIZATION = "Authorization"
private const val BEARER_TEMPLATE = "Bearer %s"

class AuthBearerInterceptor @Inject constructor(
    @RestTokenProvider private val tokenProvider: Provider<String?>
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder().apply {
            val token = tokenProvider.get() ?: return@apply
            addHeader(AUTHORIZATION, BEARER_TEMPLATE.format(token))
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

class AuthApiKeyInterceptor @Inject constructor(
    @RestApiKey private val apiKey: String
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder().apply {
            addHeader("x-api-key", apiKey)
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

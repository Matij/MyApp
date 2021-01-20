@file:Suppress("unused")

package com.martafode.lib.rest

import com.martafode.lib.di.ApplicationScoped
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Annotations for values needed to fulfill for Rest module

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class RestApiBaseUrl

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class RestApiKey

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class RestDebug

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class RestTokenProvider

// Annotations for interceptors that can be injected to OkHttp

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class OkHttpInterceptor

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class OkHttpNetworkInterceptor

@Module(includes = [RestModule.Bindings::class])
object RestModule {

    // service related - OkHttp

    // "OkHttpClients should be shared" - https://square.github.io/okhttp/3.x/okhttp/okhttp3/OkHttpClient.html
    @Provides
    @ApplicationScoped
    fun provideOkHttpClient(
        @OkHttpInterceptor interceptors: Set<@JvmSuppressWildcards Interceptor>,
        @OkHttpNetworkInterceptor networkInterceptors: Set<@JvmSuppressWildcards Interceptor>,
        @RestDebug isDebug: Boolean,
    ): OkHttpClient = OkHttpClient.Builder()
        .apply {
            val timeoutSeconds = if (isDebug) 3600L else 60L
            readTimeout(timeoutSeconds, TimeUnit.SECONDS)
            writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
            connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
        }
        .apply {
            interceptors
                .forEach {
                    addInterceptor(it)
                }
            networkInterceptors
                .forEach {
                    addNetworkInterceptor(it)
                }
        }
        .build()

    @Provides
    @IntoSet
    @OkHttpNetworkInterceptor
    fun provideHttpLoggingInterceptor(@RestDebug debug: Boolean): Interceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (debug) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        }

    // service related - Retrofit

    @Provides
    @ApplicationScoped
    fun provideRetrofit(
        @RestApiBaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    @Suppress("unused")
    @Module
    abstract class Bindings {
        // The declaration of multi-binding is necessary in case
        // if there is no bindings and set is empty.
        @Multibinds
        @OkHttpInterceptor
        abstract fun bindInterceptorsSet(): Set<Interceptor>

        @Multibinds
        @OkHttpNetworkInterceptor
        abstract fun bindNetworkInterceptorsSet(): Set<Interceptor>

        @Binds
        @IntoSet
        @OkHttpInterceptor
        abstract fun bindAuthInterceptor(bind: AuthApiKeyInterceptor): Interceptor

        @Binds
        @IntoSet
        @OkHttpInterceptor
        abstract fun bindAuthBearerInterceptor(bind: AuthBearerInterceptor): Interceptor
    }
}

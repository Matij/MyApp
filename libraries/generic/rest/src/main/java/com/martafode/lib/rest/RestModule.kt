@file:Suppress("unused")

package com.martafode.lib.rest

import android.content.Context
import com.martafode.lib.di.ApplicationScoped
import com.martafode.lib.rest.helper.NetworkConnectivity
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import okhttp3.Cache
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
        networkConnectivity: NetworkConnectivity,
        applicationRestCache: Cache,
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
            networkInterceptors
                .forEach {
                    addNetworkInterceptor(it)
                }
        }
        .cache(applicationRestCache)
        .addInterceptor { chain ->
            // Get the request from the chain.
            var request = chain.request()

            /*
            *  Leveraging the advantage of using Kotlin,
            *  we initialize the request and change its header depending on whether
            *  the device is connected to Internet or not.
            */
            request = if (networkConnectivity.hasNetwork()!!)
            /*
            *  If there is Internet, get the cache that was stored 30 minutes ago.
            *  If the cache is older than 5 seconds, then discard it,
            *  and indicate an error in fetching the response.
            *  The 'max-age' attribute is responsible for this behavior.
            */
                request.newBuilder().header("Cache-Control", "public, max-age=" + 30 * 60).build()
            else
            /*
            *  If there is no Internet, get the cache that was stored 7 days ago.
            *  If the cache is older than 7 days, then discard it,
            *  and indicate an error in fetching the response.
            *  The 'max-stale' attribute is responsible for this behavior.
            *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
            */
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
            // End of if-else statement

            // Add the modified request to the chain.
            chain.proceed(request)
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

    @Provides
    @ApplicationScoped
    fun provideRestCache(context: Context): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()
        return Cache(context.cacheDir, cacheSize)
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

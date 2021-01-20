package com.martafode.myapp.di

import com.martafode.myapp.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Named

const val BUILD_CONFIG_DEBUG = "BUILD_CONFIG_DEBUG"
const val BUILD_CONFIG_REST_API_BASE_URL = "BUILD_CONFIG_REST_API_BASE_URL"
const val BUILD_CONFIG_REST_ACCESS_KEY = "BUILD_CONFIG_REST_API_KEY"

@Module
object BuildConfigModule {
    @Provides
    @Named(BUILD_CONFIG_DEBUG)
    fun provideBuildConfigDebug(): Boolean = BuildConfig.DEBUG

    @Provides
    @Named(BUILD_CONFIG_REST_API_BASE_URL)
    fun provideBuildConfigRestApiBaseUrl(): String = BuildConfig.REST_API_BASE_URL

    @Provides
    @Named(BUILD_CONFIG_REST_ACCESS_KEY)
    fun provideBuildConfigRestAccessKey(): String = BuildConfig.REST_ACCESS_KEY
}

package com.martafode.myapp.di

import android.app.Application
import android.content.Context
import com.martafode.feature.main.di.MAIN_ACCESS_KEY
import com.martafode.feature.main.di.MAIN_IS_DEBUG_BUILD
import com.martafode.lib.rest.RestApiBaseUrl
import com.martafode.lib.rest.RestApiKey
import com.martafode.lib.rest.RestDebug
import com.martafode.myapp.MyApp
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module(
    includes = [
        BuildConfigModule::class,
        AppModule.BindingModule::class,
    ]
)
object AppModule {

    @Module
    abstract class BindingModule {
        @Binds
        abstract fun bindApplication(instance: MyApp): Application

        @Binds
        abstract fun bindContext(application: Application): Context

        // REST

        /** It is required to fulfill [RestModule]
         *  It requires information about @[RestApiBaseUrl] */
        @Binds
        @RestApiBaseUrl
        abstract fun bindRestApiBaseUrl(@Named(BUILD_CONFIG_REST_API_BASE_URL) b: String): String

        @Binds
        @RestApiKey
        abstract fun bindRestApiKey(@Named(BUILD_CONFIG_REST_ACCESS_KEY) b: String): String

        @Binds
        @RestDebug
        abstract fun bindRestDebug(@Named(BUILD_CONFIG_DEBUG) b: Boolean): Boolean

        // features/main

        @Binds
        @Named(MAIN_IS_DEBUG_BUILD)
        abstract fun bindMainIsDebugBuild(@Named(BUILD_CONFIG_DEBUG) b: Boolean): Boolean

        @Binds
        @Named(MAIN_ACCESS_KEY)
        abstract fun bindMainAccessKey(@Named(BUILD_CONFIG_REST_ACCESS_KEY) b: String): String
    }
}

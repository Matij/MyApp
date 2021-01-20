package com.martafode.myapp.di

import android.app.Application
import android.content.Context
import com.martafode.feature.main.di.MAIN_IS_DEBUG_BUILD
import com.martafode.myapp.MyApp
import dagger.Binds
import dagger.Module
import javax.inject.Named

const val BUILD_CONFIG_DEBUG = "BUILD_CONFIG_DEBUG"

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

        // features/main

        @Binds
        @Named(MAIN_IS_DEBUG_BUILD)
        abstract fun bindConsumerIsDebugBuild(@Named(BUILD_CONFIG_DEBUG) b: Boolean): Boolean
    }
}

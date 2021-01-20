package com.martafode.myapp.di

import com.martafode.myapp.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object BuildConfigModule {
    @Provides
    @Named(BUILD_CONFIG_DEBUG)
    fun provideBuildConfigDebug(): Boolean = BuildConfig.DEBUG
}

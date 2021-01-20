package com.martafode.myapp.appinitializers

import com.martafode.feature.main.di.MAIN_IS_DEBUG_BUILD
import com.martafode.lib.initializer.api.Initializer
import com.martafode.lib.logger.InitializeLogger
import com.martafode.lib.logger.api.Logger
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Named

@Module
object AppInitializersModule {
    @Provides
    @IntoSet
    fun providesLoggerInitializer(
        @Named(MAIN_IS_DEBUG_BUILD) isDebug: Boolean,
        initializeLogger: InitializeLogger,
        logger: Logger
    ): Initializer =
        object : Initializer(priority = 1) {
            override fun init() {
                initializeLogger(isDebug)
                logger.d("[init] LoggerInitializer DONE")
            }
        }
}

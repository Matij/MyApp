@file:Suppress("unused")

package com.martafode.lib.logger

import com.martafode.lib.logger.api.Logger
import dagger.Binds
import dagger.Module
import dagger.multibindings.Multibinds
import timber.log.Timber

@Module(includes = [LoggerModule.BindingModule::class])
object LoggerModule {

    @Module
    abstract class BindingModule {
        // The declaration of multi-binding is necessary in case
        // if there is no bindings and set is empty.
        // This is the case of additional apps like in largestation project: app-reardoor,
        // app-supervision, app-devpanel, that doesn't need custom additional logger trees.
        @Multibinds abstract fun bindTimberTrees(): Set<Timber.Tree>

        @Binds
        abstract fun provideLogger(bind: LoggerImpl): Logger
    }
}

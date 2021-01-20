@file:Suppress("unused")

package com.martafode.lib.initializer

import com.martafode.lib.initializer.api.AppInitializer
import com.martafode.lib.initializer.api.Initializer
import dagger.Module
import dagger.multibindings.Multibinds

@Module
abstract class InitializerModule {
    // The declaration of multi-binding is necessary in case
    // if there is no bindings and set is empty.
    @Multibinds
    abstract fun bindInitializersSet(): Set<Initializer>

    @Multibinds
    abstract fun bindAppInitializersSet(): Set<AppInitializer>
}

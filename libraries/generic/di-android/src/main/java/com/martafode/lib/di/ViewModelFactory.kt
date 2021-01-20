package com.martafode.lib.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import javax.inject.Inject
import javax.inject.Provider

/**
 * ViewModuleFactory
 *
 * Its used when we want to inject ViewModel that doesn't need
 * an initial state as a parameter.
 */
class ViewModelFactory @Inject constructor(
    private val vmProviders: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        vmProviders[modelClass]?.get() as T
}

@Suppress("unused")
@Module
abstract class ViewModelFactoryDiModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

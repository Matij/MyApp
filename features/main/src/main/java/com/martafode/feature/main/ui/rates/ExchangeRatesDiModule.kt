package com.martafode.feature.main.ui.rates

import com.martafode.feature.main.ui.MainAssistedModule
import com.martafode.feature.main.ui.rates.view.ExchangeRatesFragment
import com.martafode.lib.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ExchangeRatesDiModule.ExchangeRatesFragmentDiModule::class])
object ExchangeRatesDiModule {

    @Module
    abstract class ExchangeRatesFragmentDiModule {
        @ContributesAndroidInjector(
            modules = [
                ExchangeRatesViewModelDiModule::class // fragment-scoped viewmodel instance
            ]
        )
        @FragmentScoped
        abstract fun contributeSubcomponent(): ExchangeRatesFragment
    }

    /** Necessary to instantiate ExchangeRatesViewModel. */
    @Module(includes = [MainAssistedModule::class])
    abstract class ExchangeRatesViewModelDiModule
}

package com.martafode.feature.main.ui

import com.martafode.feature.main.ui.rates.ExchangeRatesDiModule
import com.martafode.lib.di.ActivityScoped
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [MainUiModule.MainActivityDiModule::class])
class MainUiModule {
    @Module
    abstract class MainActivityDiModule {
        @ContributesAndroidInjector(
            modules = [
                ExchangeRatesDiModule::class,
                // activity scoped view models
                MainAssistedModule::class,
            ]
        )
        @ActivityScoped
        abstract fun contributeSubcomponent(): MainActivity
    }
}

@Module(includes = [AssistedInject_MainAssistedModule::class])
@AssistedModule
interface MainAssistedModule

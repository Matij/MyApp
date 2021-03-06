package com.martafode.myapp.di

import com.martafode.feature.main.MainModule
import com.martafode.lib.concurrency.ConcurrencyModule
import com.martafode.lib.di.ApplicationScoped
import com.martafode.lib.initializer.InitializerModule
import com.martafode.lib.json.JsonModule
import com.martafode.lib.logger.LoggerModule
import com.martafode.lib.rest.RestModule
import com.martafode.myapp.MyApp
import com.martafode.myapp.appinitializers.AppInitializersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@ApplicationScoped
@Component(
    modules = [
        // libraries - generic
        ConcurrencyModule::class,
        InitializerModule::class,
        JsonModule::class,
        LoggerModule::class,
        RestModule::class,
        // libraries - specific
        // features
        MainModule::class,
        // other
        AppModule::class,
        AppInitializersModule::class,
        AndroidInjectionModule::class,
    ]
)
interface AppComponent : AndroidInjector<MyApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: MyApp): AppComponent
    }
}

fun MyApp.createComponent() = DaggerAppComponent.factory().create(this)

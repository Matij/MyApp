package com.martafode.myapp.di

import com.martafode.feature.main.MainModule
import com.martafode.lib.di.ApplicationScoped
import com.martafode.lib.rest.RestModule
import com.martafode.myapp.MyApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@ApplicationScoped
@Component(
    modules = [
        // libraries - generic
        RestModule::class,
        // libraries - specific
        // features
        MainModule::class,
        // other
        AppModule::class,
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

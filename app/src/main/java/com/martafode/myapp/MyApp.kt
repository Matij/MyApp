package com.martafode.myapp

import com.martafode.lib.initializer.AppInitializers
import com.martafode.myapp.di.createComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class MyApp: DaggerApplication() {
    @Inject
    lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = createComponent()
}

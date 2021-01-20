package com.martafode.myapp

import com.martafode.myapp.di.createComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApp: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = createComponent()
}

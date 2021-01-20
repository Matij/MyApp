package com.martafode.myapp.di

import android.app.Application
import android.content.Context
import com.martafode.myapp.MyApp
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        AppModule.BindingModule::class,
    ]
)
object AppModule {

    @Module
    abstract class BindingModule {
        @Binds
        abstract fun bindApplication(instance: MyApp): Application

        @Binds
        abstract fun bindContext(application: Application): Context
    }
}

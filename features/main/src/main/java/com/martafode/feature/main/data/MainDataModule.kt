package com.martafode.feature.main.data

import com.martafode.feature.main.data.remote.RemoteModule
import dagger.Module

@Module(includes = [
    RemoteModule::class,
    MainDataModule.BindingModule::class
])
object MainDataModule {
    @Module
    abstract class BindingModule
}

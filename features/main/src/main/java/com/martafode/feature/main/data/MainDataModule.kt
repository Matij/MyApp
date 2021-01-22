package com.martafode.feature.main.data

import com.martafode.feature.main.data.local.LocalModule
import com.martafode.feature.main.data.remote.RemoteModule
import dagger.Module

@Module(includes = [
    RemoteModule::class,
    LocalModule::class,
    MainDataModule.BindingModule::class
])
object MainDataModule {
    @Module
    abstract class BindingModule
}

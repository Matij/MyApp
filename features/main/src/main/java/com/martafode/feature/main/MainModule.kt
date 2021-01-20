package com.martafode.feature.main

import com.martafode.feature.main.data.MainDataModule
import com.martafode.feature.main.ui.MainUiModule
import dagger.Module

@Module(includes = [
    MainDataModule::class,
    MainUiModule::class,
])
object MainModule

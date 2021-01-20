package com.martafode.feature.main

import com.martafode.feature.main.ui.MainUiModule
import dagger.Module

@Module(includes = [
    MainUiModule::class
])
object MainModule

package com.martafode.feature.main.data.local

import android.content.Context
import com.martafode.lib.di.*
import dagger.Module
import dagger.Provides

const val APP_PREFERENCES_NAME = "MyAppSharedPreferences"
const val DATA_PREFERENCES_NAME = "MyAppDataSharedPreferences"

@Module
object LocalModule {

    @AppPreferenceInfo
    @Provides
    fun provideAppPreferenceName(): String = APP_PREFERENCES_NAME

    @DataPreferenceInfo
    @Provides
    fun provideDataPreferenceName(): String = DATA_PREFERENCES_NAME

    @ApplicationScoped
    @Provides
    fun provideAppPreferencesHelper(
        context: Context,
        @AppPreferenceInfo appPrefFileName: String,
    ): AppPreferencesHelper {
        return AppPreferencesHelper(
            appPreferences = context.getSharedPreferences(appPrefFileName, Context.MODE_PRIVATE),
        )
    }

    @ApplicationScoped
    @Provides
    fun provideDataPreferencesHelper(
        context: Context,
        @DataPreferenceInfo dataPrefFileName: String,
    ): DataPreferencesHelper {
        return DataPreferencesHelper(
            dataPreferences = context.getSharedPreferences(dataPrefFileName, Context.MODE_PRIVATE),
        )
    }
}

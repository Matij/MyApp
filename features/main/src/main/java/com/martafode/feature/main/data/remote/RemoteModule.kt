package com.martafode.feature.main.data.remote

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class RemoteModule {
    @Provides
    fun providesApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}

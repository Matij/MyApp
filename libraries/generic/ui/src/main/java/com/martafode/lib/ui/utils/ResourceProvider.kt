package com.martafode.lib.ui.utils

import android.content.Context
import androidx.annotation.StringRes
import dagger.Binds
import dagger.Module
import javax.inject.Inject

interface ResourceProvider {
    fun getString(@StringRes stringId: Int): String
    fun convertToString(sth: Any?): String?
}

class ResourceProviderImpl @Inject constructor(
    private val context: Context
) : ResourceProvider {

    override fun getString(stringId: Int) = context.getString(stringId)
    override fun convertToString(sth: Any?): String? =
        when (sth) {
            is Int -> getString(sth)
            is String -> sth
            else -> null
        }
}

@Suppress("unused")
@Module
abstract class ResourceProviderModule {
    @Binds
    abstract fun bindResourceProviderImpl(b: ResourceProviderImpl): ResourceProvider
}

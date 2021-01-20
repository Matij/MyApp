package com.martafode.lib.initializer

import android.app.Application
import com.martafode.lib.di.ApplicationScoped
import com.martafode.lib.initializer.api.AppInitializer
import com.martafode.lib.initializer.api.Initializer
import javax.inject.Inject

@ApplicationScoped
class AppInitializers @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards Initializer>,
    private val appInitializers: Set<@JvmSuppressWildcards AppInitializer>
) {
    fun init(application: Application) {
        initializers.map { it.toAppInitializer() }
            .plus(appInitializers)
            .sortedBy { it.priority }
            .forEach { it.init(application) }
    }
}

private fun Initializer.toAppInitializer() = object : AppInitializer(priority) {
    override fun init(application: Application) = init()
}

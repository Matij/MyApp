package com.martafode.lib.initializer.api

import android.app.Application
import androidx.annotation.IntRange

abstract class AppInitializer(
    /** Priority value - helps to order launching AppInitializers.
     * Takes values from 0 to 127. */
    @IntRange(from = 0, to = 127) val priority: Int = DEFAULT_PRIORITY
) {
    abstract fun init(application: Application)
}

// CONSIDER: AppInitializer can be removed
//   Simple Initializer is enough. Android specific initializers can have
//   Application injected if they are needed.

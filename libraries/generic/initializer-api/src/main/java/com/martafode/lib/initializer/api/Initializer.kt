package com.martafode.lib.initializer.api

import androidx.annotation.IntRange

const val DEFAULT_PRIORITY = 64

abstract class Initializer(
    /** Priority value - helps to order launching Initializers.
     * Takes values from 0 to 127. */
    @IntRange(from = 0, to = 127) val priority: Int = DEFAULT_PRIORITY
) {
    abstract fun init()
}

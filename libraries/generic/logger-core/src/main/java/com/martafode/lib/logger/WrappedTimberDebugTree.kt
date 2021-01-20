package com.martafode.lib.logger

import timber.log.Timber

/**
 * Special version of [Timber.DebugTree] which is tailored for Timber being wrapped
 * within another class, like LoggerImpl.
 */
open class WrappedTimberDebugTree : Timber.DebugTree() {
    // Since Tree.getTag is package-private I can't override it.
    // But I can override the `log()` method and use my own `getTag()`.
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, getTag(), message, t)
    }

    /**
     * Provides the tag for the log.
     *
     * The crucial difference compare to Timber.DebugTree.getTag is CALL_STACK_INDEX
     * In original implementation is set to 5, here to 7.
     */
    protected fun getTag(): String? {
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size <= CALL_STACK_INDEX) {
            val msg = "Synthetic stacktrace didn't have enough elements: are you using proguard?"
            throw IllegalStateException(msg)
        }
        return createStackElementTag(stackTrace[CALL_STACK_INDEX])
    }

    companion object {
        private const val CALL_STACK_INDEX = 7
    }
}

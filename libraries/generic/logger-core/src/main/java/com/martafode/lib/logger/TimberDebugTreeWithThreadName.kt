package com.martafode.lib.logger

import timber.log.Timber

/**
 * Special version of [Timber.DebugTree] which is tailored for Timber being wrapped
 * within another class.
 */
class TimberDebugTreeWithThreadName : WrappedTimberDebugTree() {
    override fun createStackElementTag(e: StackTraceElement) =
        String.format("%s [%s]", super.createStackElementTag(e), Thread.currentThread().name)
}
// https://stackoverflow.com/questions/38689399/log-method-name-and-line-number-in-timber/49216400#49216400
// https://stackoverflow.com/questions/38689399/log-method-name-and-line-number-in-timber/38689400#38689400

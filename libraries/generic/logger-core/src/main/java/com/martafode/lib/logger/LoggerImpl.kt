package com.martafode.lib.logger

import com.martafode.lib.di.ApplicationScoped
import com.martafode.lib.logger.api.Logger
import com.martafode.lib.logger.api.SimplifiedTree
import javax.inject.Inject
import timber.log.Timber

@ApplicationScoped
class LoggerImpl @Inject constructor() : Logger {

    fun setup(debugMode: Boolean, trees: Set<Timber.Tree>) {
        if (debugMode) {
            Timber.plant(WrappedTimberDebugTree())
//            Timber.plant(TimberDebugTreeWithThreadName())
        }
        trees.forEach {
            Timber.plant(it)
        }
    }

    override fun plant(tree: SimplifiedTree) {
        Timber.plant(
            object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    tree.log(priority, tag, message, t)
                }
            }
        )
    }

    override fun v(message: String, vararg args: Any?) {
        Timber.v(message, *args)
    }

    override fun v(t: Throwable, message: String, vararg args: Any?) {
        Timber.v(t, message, *args)
    }

    override fun v(t: Throwable) {
        Timber.v(t)
    }

    override fun d(message: String, vararg args: Any?) {
        Timber.d(message, *args)
    }

    override fun d(t: Throwable, message: String, vararg args: Any?) {
        Timber.d(t, message, *args)
    }

    override fun d(t: Throwable) {
        Timber.d(t)
    }

    override fun i(message: String, vararg args: Any?) {
        Timber.i(message, *args)
    }

    override fun i(t: Throwable, message: String, vararg args: Any?) {
        Timber.i(t, message, *args)
    }

    override fun i(t: Throwable) {
        Timber.i(t)
    }

    override fun w(message: String, vararg args: Any?) {
        Timber.w(message, *args)
    }

    override fun w(t: Throwable, message: String, vararg args: Any?) {
        Timber.w(t, message, *args)
    }

    override fun w(t: Throwable) {
        Timber.w(t)
    }

    override fun e(message: String, vararg args: Any?) {
        Timber.e(message, *args)
    }

    override fun e(t: Throwable, message: String, vararg args: Any?) {
        Timber.e(t, message, *args)
    }

    override fun e(t: Throwable) {
        Timber.e(t)
    }

    override fun wtf(message: String, vararg args: Any?) {
        Timber.wtf(message, *args)
    }

    override fun wtf(t: Throwable, message: String, vararg args: Any?) {
        Timber.wtf(t, message, *args)
    }

    override fun wtf(t: Throwable) {
        Timber.wtf(t)
    }
}

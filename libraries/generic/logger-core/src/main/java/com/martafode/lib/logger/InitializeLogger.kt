package com.martafode.lib.logger

import javax.inject.Inject
import timber.log.Timber

class InitializeLogger @Inject constructor(
    private val logger: LoggerImpl,
    // `trees` can be empty (thanks to multi-binding declaration in LoggerModule)
    private val trees: Set<@JvmSuppressWildcards Timber.Tree>
) {
    // Timber is independent component, and it is used everywhere.
    // If some other initializer is executed earlier, logging there will
    // be lost. That's why the logger should be implemented with one of
    // the highest priority.

    operator fun invoke(isDebug: Boolean) = logger.setup(isDebug, trees)
}

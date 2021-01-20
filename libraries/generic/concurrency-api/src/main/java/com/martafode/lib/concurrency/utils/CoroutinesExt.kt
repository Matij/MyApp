package com.martafode.lib.concurrency.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeoutOrNull

// The method retries to execute the block given amount of times,
// and posts updates about failing executions.
// Inspired by:
// https://stackoverflow.com/questions/46872242/how-to-exponential-backoff-retry-on-kotlin-coroutines/46890009#46890009
suspend fun <T> retryWithTimeoutOrNull(
    times: Int = Int.MAX_VALUE,
    timeout: Long = 100, // 100ms
    initialDelay: Long = 100, // 100ms
    maxDelay: Long = 1_000, // 1s
    factor: Double = 2.0,
    postTryBlock: (Int) -> Unit = {},
    block: suspend () -> T
): T? {
    @Suppress("UNUSED_VARIABLE")
    var currentDelay = initialDelay
    repeat(times) { tryNo ->
        val res = withTimeoutOrNull(timeout) {
            block()
        }
        if (res != null) return res
        postTryBlock(tryNo)
        // don't delay on the last iteration
        if (tryNo < times - 1) {
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
    }
    return null
}

suspend fun every(initialDelay: Long, repeatDelay: Long, block: suspend () -> Unit) {
    intervalTimestampRx(initialDelay, repeatDelay).collect { block() }
}

suspend inline fun everyMinute(initialDelay: Long = 0, noinline block: suspend () -> Unit) =
    every(initialDelay, 60_000, block)

suspend inline fun everyThreeMinutes(initialDelay: Long = 0, noinline block: suspend () -> Unit) =
    every(initialDelay, 3 * 60_000, block)

suspend inline fun everyHour(initialDelay: Long = 0, noinline block: suspend () -> Unit) =
    every(initialDelay, 60 * 60_000, block)

fun everyMinuteFlow(initialDelay: Long = 0) = flow { everyMinute(initialDelay) { emit(Unit) } }

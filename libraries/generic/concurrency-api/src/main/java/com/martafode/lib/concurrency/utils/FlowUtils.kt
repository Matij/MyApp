package com.martafode.lib.concurrency.utils

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow

fun intervalTimestampRx(delayMillis: Long = 0, repeatMillis: Long = 0): Flow<Long> =
    Observable.interval(delayMillis, repeatMillis, TimeUnit.MILLISECONDS)
        .toFlowable(BackpressureStrategy.LATEST)
        .asFlow()

// https://prashamhtrivedi.in/coroutines.html#channels
// https://kotlinlang.org/docs/reference/coroutines/channels.html#ticker-channels
// https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.concurrent/fixed-rate-timer.html

// https://github.com/Kotlin/kotlinx.coroutines/issues/1446#issuecomment-625244176
fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> {
    var job: Job = Job().apply { complete() }
    return onCompletion { job.cancel() }.run {
        flow {
            coroutineScope {
                collect { value ->
                    if (!job.isActive) {
                        emit(value)
                        job = launch { delay(windowDuration) }
                    }
                }
            }
        }
    }
}

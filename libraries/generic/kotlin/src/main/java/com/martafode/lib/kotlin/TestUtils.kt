package com.martafode.lib.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

suspend fun delayCounter(times: Int, delayTime: Long = 1_000, block: suspend (Int) -> Unit) {
    block(times)
    repeat(times) { i ->
        block(times - i - 1)
        delay(delayTime)
    }
}

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun blockingDelayCounter(times: Int, delayTime: Long = 1_000, block: suspend (Int) -> Unit) {
    block(times)
    repeat(times) { i ->
        block(times - i - 1)
        Thread.sleep(delayTime)
    }
}

class FlowObserver<T> constructor(private val hotFlow: Flow<T>, private val scope: CoroutineScope) {
    private val collectedItems: MutableList<T> = mutableListOf()
    private val subscriptionJob: Job = startSubscription()

    private fun startSubscription(): Job = scope.launch {
        hotFlow.collect { collectedItems.add(it) }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun stopSubscription() = subscriptionJob.cancel()

    fun getContent(): List<T> {
        stopSubscription()
        return collectedItems
    }
}

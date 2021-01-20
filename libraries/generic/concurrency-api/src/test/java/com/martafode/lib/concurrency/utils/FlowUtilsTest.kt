package com.martafode.lib.concurrency.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FlowUtilsTest {

    // https://github.com/Kotlin/kotlinx.coroutines/issues/1446#issuecomment-625244176
    @Test
    fun throttleFirst() = runBlockingTest {
        val testDispatcher = TestCoroutineDispatcher()
        val values = mutableListOf<Int>()
        // given
        val flow = (1..10).asFlow().onEach { delay(200) }
        // when
        flow
            .throttleFirst(500)
            .flowOn(testDispatcher)
            .onEach { values.add(it) }
            .launchIn(this)
        testDispatcher.advanceTimeBy(2000)
        // then
        assertEquals(listOf(1, 4, 7, 10), values)
    }

    @Test
    fun throttleFirstWithError() = runBlockingTest {
        val testDispatcher = TestCoroutineDispatcher()
        val values = mutableListOf<Int>()
        // given
        val flow = (1..10).asFlow()
            .onEach { delay(200) }
            .onEach { if (it == 2) throw IllegalStateException() }
        // when
        flow
            .throttleFirst(500)
            .flowOn(testDispatcher)
            .onEach { values.add(it) }
            .catch { }
            .launchIn(this)
        testDispatcher.advanceTimeBy(400)
        // then
        assertEquals(listOf(1), values)
    }
}

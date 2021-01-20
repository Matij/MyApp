package com.martafode.lib.kotlin

import kotlin.random.Random

private val charPool = ('a'..'z').plus('A'..'Z').plus('0'..'9').toCharArray()

fun randomString(length: Int) =
    (1..length)
        .map { charPool[Random.nextInt(0, charPool.size)] }
        .joinToString("")

fun String.Companion.random(length: Int) = randomString(length)

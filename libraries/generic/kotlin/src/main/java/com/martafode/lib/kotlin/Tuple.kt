package com.martafode.lib.kotlin

// Set of Tuples
// Inspired by MvrxTuples:
// https://github.com/airbnb/MvRx/blob/2f9edf1/mvrx/src/main/kotlin/com/airbnb/mvrx/MvRxTuples.kt

data class Tuple1<A>(val a: A)
data class Tuple2<A, B>(val a: A, val b: B)
data class Tuple3<A, B, C>(val a: A, val b: B, val c: C)
data class Tuple4<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)
data class Tuple5<A, B, C, D, E>(val a: A, val b: B, val c: C, val d: D, val e: E)
data class Tuple6<A, B, C, D, E, F>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F)
data class Tuple7<A, B, C, D, E, F, G>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G)

fun <A> tupleOf(a: A) = Tuple1(a)
fun <A, B> tupleOf(a: A, b: B) = Tuple2(a, b)
fun <A, B, C> tupleOf(a: A, b: B, c: C) = Tuple3(a, b, c)
fun <A, B, C, D> tupleOf(a: A, b: B, c: C, d: D) = Tuple4(a, b, c, d)
fun <A, B, C, D, E> tupleOf(a: A, b: B, c: C, d: D, e: E) = Tuple5(a, b, c, d, e)
fun <A, B, C, D, E, F> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F) = Tuple6(a, b, c, d, e, f)
fun <A, B, C, D, E, F, G> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G) = Tuple7(a, b, c, d, e, f, g)

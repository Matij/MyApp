package com.martafode.lib.kotlin

fun <A, B, R> partial2(f: (A, B) -> R, a: A): (B) -> R {
    return { b: B -> f(a, b) }
}

fun <A, B, R> Function2<A, B, R>.partial(a: A): (B) -> R {
    return { b -> invoke(a, b) }
}

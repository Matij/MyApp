package com.martafode.lib.kotlin

// nested groupBy - generic

fun <T> List<T>.groupByNested(vararg selectorList: (T) -> Any): Map<Any, Any> = this
    .groupBy(selectorList.first())
    .mapValues {
        if (selectorList.size > 1) it.value.groupByNested(*selectorList.drop(1).toTypedArray())
        else it.value
    }

// nested groupBy - typed

fun <T, K> List<T>.groupByNested(firstSelector: (T) -> K): Map<K, List<T>> = this
    .groupBy(firstSelector)

fun <T, K, L> List<T>.groupByNested(
    firstSelector: (T) -> K,
    secondSelector: (T) -> L
): Map<K, Map<L, List<T>>> = this
    .groupBy(firstSelector)
    .mapValues {
        it.value
            .groupBy(secondSelector)
    }

fun <T, K, L, M> List<T>.groupByNested(
    firstSelector: (T) -> K,
    secondSelector: (T) -> L,
    thirdSelector: (T) -> M
): Map<K, Map<L, Map<M, List<T>>>> = this
    .groupBy(firstSelector)
    .mapValues {
        it.value
            .groupBy(secondSelector)
            .mapValues {
                it.value
                    .groupBy(thirdSelector)
            }
    }

// insert given item between all list items

fun <T> List<T>.insertBetween(item: T): List<T> {
    if (isEmpty()) return this
    val list = mutableListOf(this.first())
    drop(1).forEach { list.addAll(listOf(item, it)) }
    return list
}

// Instead of long string prints short
// E.g. instead of typical toString() like `servedup.terminal.feature.home.data.AcceptOrderFn@11ed7e8` prints short `AcceptOrderFn@11ed7e8`

val <T : Any> T.address get(): String = "${this::class.java.simpleName}@${hashCode().toString(16)}"

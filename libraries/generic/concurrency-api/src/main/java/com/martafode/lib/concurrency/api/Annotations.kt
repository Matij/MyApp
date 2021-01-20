// ktlint-disable filename
@file:Suppress("unused")

package com.martafode.lib.concurrency.api

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class Global

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class ProcessLifetime

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class ComputationDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class IoDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class MainDispatcher

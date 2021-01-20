package com.martafode.lib.concurrency.api

import io.reactivex.Scheduler

data class AppRxSchedulers(
    val io: Scheduler,
    val computation: Scheduler,
    val main: Scheduler,
    val newThread: Scheduler
)

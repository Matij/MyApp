package com.martafode.lib.ui.utils

import android.os.SystemClock
import android.view.View
import java.util.concurrent.atomic.AtomicLong

fun createOnClickListenerDebounced(
    elapsed: Long = 500L,
    listener: View.OnClickListener
): View.OnClickListener {
    val lastClickTime = AtomicLong(0)
    return View.OnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime.get() < elapsed) return@OnClickListener
        listener.onClick(it)
        lastClickTime.set(SystemClock.elapsedRealtime())
    }
}

inline fun createOnClickListenerDebounced(elapsed: Long = 500L, crossinline listener: () -> Unit) =
    createOnClickListenerDebounced(elapsed, View.OnClickListener { listener() })

fun View.setOnClickListenerDebounced(elapsed: Long = 500L, listener: View.OnClickListener) =
    setOnClickListener(createOnClickListenerDebounced(elapsed, listener))

inline fun View.setOnClickListenerDebounced(elapsed: Long = 500L, crossinline listener: () -> Unit) =
    setOnClickListenerDebounced(elapsed, View.OnClickListener { listener() })

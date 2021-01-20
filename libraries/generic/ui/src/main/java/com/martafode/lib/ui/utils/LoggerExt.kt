package com.martafode.lib.ui.utils

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleService
import com.martafode.lib.kotlin.address
import com.martafode.lib.logger.api.Logger

fun Logger.logActivityLifecycleEvents(activity: ComponentActivity) {
    activity.lifecycle.addObserver(
        LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->
            d(";;;; ACTIVITY, event=$event, source=${source.address}")
        }
    )
}

fun Logger.logFragmentLifecycleEvents(fragment: Fragment) {
    fragment.lifecycle.addObserver(
        LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->
            d(";;;; FRAGMENT, event=$event, source=${source.address}, activity=${fragment.requireActivity().address}")
        }
    )
}

fun Logger.logFragmentViewLifecycleEvents(fragment: Fragment) {
    fragment.viewLifecycleOwner.lifecycle.addObserver(
        LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->
            d(";;;; VIEW,     event=$event, source=${source.address}, fragment=${fragment.address}")
        }
    )
}

fun Logger.logServiceLifecycleEvents(service: LifecycleService) {
    service.lifecycle.addObserver(
        LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->
            d(";;;; SERVICE,  event=$event, source=${source.address}")
        }
    )
}

// -- logActivityLifecycleEvents with lazy evaluation of Logger

fun logActivityLifecycleEvents(activity: ComponentActivity, getLoggerFn: () -> Logger?) {
    activity.lifecycle.addObserver(
        LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->
            getLoggerFn()?.d(";;;; ACTIVITY, event=$event, source=${source.address}")
        }
    )
}

fun logFragmentLifecycleEvents(fragment: Fragment, getLoggerFn: () -> Logger?) {
    fragment.lifecycle.addObserver(
        LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->
            val activity = fragment.requireActivity().address
            getLoggerFn()?.d(";;;; FRAGMENT, event=$event, source=${source.address}, activity=$activity")
        }
    )
}

package com.martafode.lib.ui.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

fun Lifecycle.addObserver(event: Lifecycle.Event, block: (source: LifecycleOwner) -> Unit) =
    addObserver(
        LifecycleEventObserver { receivedSource, receivedEvent -> if (event == receivedEvent) block(receivedSource) }
    )

fun RecyclerView.clearAdapterOnViewDestroy(viewLifecycleOwner: LifecycleOwner) =
    viewLifecycleOwner.lifecycle.addObserver(Lifecycle.Event.ON_DESTROY) { adapter = null }

fun Fragment.onViewDestroy(block: (source: LifecycleOwner) -> Unit) =
    viewLifecycleOwner.lifecycle.addObserver(Lifecycle.Event.ON_DESTROY, block)

package com.martafode.lib.ui.mvrx

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.martafode.lib.ui.mvi.MviIntent
import com.martafode.lib.ui.mvi.MviIntentsConsumer
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.KProperty1

abstract class CustomBaseMvRxViewModel<S : MvRxState>(initialState: S, debugMode: Boolean) :
    BaseMvRxViewModel<S>(initialState, debugMode) {

    suspend fun getState() = suspendCoroutine<S> { cont ->
        withState { state: S -> cont.resumeWith(Result.success(state)) }
    }

    suspend fun <A> getState(prop: KProperty1<S, A>) = prop.get(getState())

    suspend fun <A, B> getState(propA: KProperty1<S, A>, propB: KProperty1<S, B>): Pair<A, B> {
        val state = getState()
        return Pair(propA.get(state), propB.get(state))
    }
}

abstract class BaseMviViewModel<S : MvRxState, I : MviIntent>(initialState: S, debugMode: Boolean) :
    CustomBaseMvRxViewModel<S>(initialState, debugMode = debugMode),
    MviIntentsConsumer<I>

typealias BaseViewModel<S, I> = BaseMviViewModel<S, I>

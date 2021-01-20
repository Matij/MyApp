package com.martafode.lib.ui.mvrx

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.lifecycle.coroutineScope
import com.airbnb.mvrx.BaseMvRxFragment
import com.martafode.lib.ui.mvi.MviIntent
import com.martafode.lib.ui.mvi.MviIntentsEmitter
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

abstract class BaseMviFragment<I : MviIntent> : BaseFragment, MviIntentsEmitter<I> {
    protected val intentsChannel: BroadcastChannel<I> = BroadcastChannel(Channel.BUFFERED)

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int = 0) : super(contentLayoutId)

    override fun intents(): Flow<I> = intentsChannel.asFlow()

    protected fun sendIntent(intent: I) = lifecycle.coroutineScope.launch {
        intentsChannel.send(intent)
    }

    protected fun I.publish() = sendIntent(this)
}

/**
 * BaseFragment
 */
abstract class BaseFragment : MyBaseMvRxDaggerFragment {
    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int = 0) : super(contentLayoutId)

    // Default invalidate method (it is rarely useful to have it in Fragments).
    override fun invalidate() {}
}

/**
 * MyBaseMvRxDaggerFragment - merge of BaseMvRxDaggerFragment and MyDaggerFragment
 */
abstract class MyBaseMvRxDaggerFragment : BaseMvRxFragment, HasAndroidInjector {
    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int = 0) : super(contentLayoutId)

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}

package com.martafode.feature.main.ui.rates

import com.airbnb.mvrx.ActivityViewModelContext
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.martafode.feature.main.di.MAIN_IS_DEBUG_BUILD
import com.martafode.feature.main.ui.MainActivity
import com.martafode.feature.main.ui.rates.view.ExchangeRatesFragment
import com.martafode.lib.ui.mvrx.BaseViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import javax.inject.Named

typealias ViewModel = ExchangeRatesViewModel

class ExchangeRatesViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    @Named(MAIN_IS_DEBUG_BUILD) isDebug: Boolean
) : BaseViewModel<State, Intent>(initialState, isDebug) {

    override suspend fun processIntents(intents: Flow<Intent>) {

    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: State): ViewModel
    }

    companion object : MvRxViewModelFactory<ViewModel, State> {
        override fun create(viewModelContext: ViewModelContext, state: State) = when (viewModelContext) {
            is ActivityViewModelContext -> viewModelContext.activity<MainActivity>().exchangeRatesViewModelFactory
            is FragmentViewModelContext -> viewModelContext.fragment<ExchangeRatesFragment>().exchangeRatesViewModelFactory
        }.create(state)
    }
}

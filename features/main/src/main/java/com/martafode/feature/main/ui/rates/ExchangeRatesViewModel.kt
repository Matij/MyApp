package com.martafode.feature.main.ui.rates

import com.airbnb.mvrx.ActivityViewModelContext
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.martafode.feature.main.data.rate.ExchangeRatesRepository
import com.martafode.feature.main.di.MAIN_IS_DEBUG_BUILD
import com.martafode.feature.main.ui.MainActivity
import com.martafode.feature.main.ui.rates.view.ExchangeRatesFragment
import com.martafode.lib.logger.api.Logger
import com.martafode.lib.rest.helper.ResultWrapper
import com.martafode.lib.ui.mvrx.BaseViewModel
import com.martafode.lib.ui.utils.viewModelLaunch
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Named

typealias ViewModel = ExchangeRatesViewModel

class ExchangeRatesViewModel @AssistedInject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository,
    private val logger: Logger,
    @Assisted initialState: State,
    @Named(MAIN_IS_DEBUG_BUILD) isDebug: Boolean
) : BaseViewModel<State, Intent>(initialState, isDebug) {

    override suspend fun processIntents(intents: Flow<Intent>) = intents.collect { intent ->
        logger.d("received: $intent")
        when (intent) {
            is ExchangeRatesIntent.Opened -> init()
        }
    }

    private fun init() {
        fetchLiveRates()
    }

    private fun fetchLiveRates() = viewModelLaunch {
        executeRequest(
            block = { exchangeRatesRepository.fetchLiveRates() },
            onSuccess = {
                logger.i("received #${it.size} items")
            }
        )
    }

    private suspend fun <T> executeRequest(
        onSuccess: ((T) -> Unit)? = null,
        onError: (() -> Unit)? = null,
        block: suspend () -> ResultWrapper<T>
    ): T? {
        fun reportError(t: Throwable) {
            if (onError != null) { onError(); return }
            logger.e(t)
            setState { copy(errorMessage = "ERROR: $t") }
        }
        val retValue = when (val response = block()) {
            is ResultWrapper.Success<T> -> response.value
            is ResultWrapper.GenericError -> {
                reportError(response.origin)
                null
            }
            is ResultWrapper.NetworkError -> { reportError(response.origin); null }
            is ResultWrapper.ParsingError -> { reportError(response.origin); null }
        }
        if (onSuccess != null && retValue != null) onSuccess(retValue)
        return retValue
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

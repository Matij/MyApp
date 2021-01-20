package com.martafode.feature.main.ui.rates.view

import com.airbnb.mvrx.fragmentViewModel
import com.martafode.feature.main.R
import com.martafode.feature.main.ui.rates.ExchangeRatesIntent
import com.martafode.feature.main.ui.rates.ExchangeRatesViewModel
import com.martafode.lib.ui.mvrx.BaseMviFragment
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExchangeRatesFragment: BaseMviFragment<ExchangeRatesIntent>(R.layout.fragment_exchange_rates) {

    @Inject lateinit var exchangeRatesViewModelFactory: ExchangeRatesViewModel.Factory
    private val exchangeRatesViewModel: ExchangeRatesViewModel by fragmentViewModel()

    override fun intents(): Flow<ExchangeRatesIntent> {
        return super.intents()
    }
}

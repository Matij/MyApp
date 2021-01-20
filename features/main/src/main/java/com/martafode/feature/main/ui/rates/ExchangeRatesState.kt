package com.martafode.feature.main.ui.rates

import com.airbnb.mvrx.MvRxState

typealias State = ExchangeRatesState

data class ExchangeRatesState(
    val errorMessage: String = "",
) : MvRxState

package com.martafode.feature.main.ui.rates

import com.airbnb.mvrx.MvRxState

typealias State = ExchangeRatesState

data class ExchangeRatesState(
    val currencies: List<String> = emptyList(),
    val quotes: List<String> = emptyList(),
    val defaultCurrencyCode: String = "USD",
    val currentCurrencyCode: String = "",
    val currentAmount: String = "",
    val conversionResult: String = "0",
    val errorMessage: String = "",
    val isLoading: Boolean = false,
) : MvRxState

package com.martafode.feature.main.ui.rates

import com.martafode.lib.ui.mvi.MviIntent

typealias Intent = ExchangeRatesIntent

sealed class ExchangeRatesIntent : MviIntent {
    object Opened: ExchangeRatesIntent()
    data class AmountSelected(val amount: String): ExchangeRatesIntent()
    data class CurrencySelected(val position: Int): ExchangeRatesIntent()
}

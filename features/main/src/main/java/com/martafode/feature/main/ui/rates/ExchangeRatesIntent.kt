package com.martafode.feature.main.ui.rates

import com.martafode.lib.ui.mvi.MviIntent

typealias Intent = ExchangeRatesIntent

sealed class ExchangeRatesIntent : MviIntent {
    object Opened: ExchangeRatesIntent()
}

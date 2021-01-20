package com.martafode.feature.main.ui

import com.martafode.feature.main.R
import com.martafode.feature.main.ui.rates.ExchangeRatesViewModel
import com.martafode.lib.ui.mvrx.BaseActivity
import javax.inject.Inject

class MainActivity: BaseActivity(R.layout.activity_main) {
    @Inject
    lateinit var exchangeRatesViewModelFactory: ExchangeRatesViewModel.Factory
}

package com.martafode.feature.main.ui.rates.view

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.martafode.feature.main.R
import com.martafode.feature.main.ui.rates.ExchangeRatesIntent
import com.martafode.feature.main.ui.rates.ExchangeRatesViewModel
import com.martafode.lib.logger.api.Logger
import com.martafode.lib.ui.mvrx.BaseMviFragment
import com.martafode.lib.ui.utils.fragmentLaunch
import com.martafode.lib.ui.utils.logFragmentLifecycleEvents
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExchangeRatesFragment: BaseMviFragment<ExchangeRatesIntent>(R.layout.fragment_exchange_rates) {

    @Inject lateinit var exchangeRatesViewModelFactory: ExchangeRatesViewModel.Factory
    private val viewModel: ExchangeRatesViewModel by fragmentViewModel()

    @Inject lateinit var logger: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        logger.logFragmentLifecycleEvents(this)
        super.onCreate(savedInstanceState)
        // wire view intents
        fragmentLaunch { viewModel.processIntents(intents()) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // OUTPUTS - to the system
        // OUTPUTS - to the user (like visual effects)
        // INPUTS - from the system
        notifyTableSelectionStartup()
        // INPUTS - from the user (like touches)
    }

    override fun intents(): Flow<ExchangeRatesIntent> {
        return super.intents()
    }

    // INPUTS - from the system

    private fun notifyTableSelectionStartup() = ExchangeRatesIntent.Opened.publish()
}

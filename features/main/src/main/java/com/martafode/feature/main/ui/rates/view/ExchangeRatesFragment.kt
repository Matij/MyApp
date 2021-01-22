package com.martafode.feature.main.ui.rates.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.airbnb.mvrx.fragmentViewModel
import com.martafode.feature.main.R
import com.martafode.feature.main.ui.rates.ExchangeRatesIntent
import com.martafode.feature.main.ui.rates.ExchangeRatesState
import com.martafode.feature.main.ui.rates.ExchangeRatesViewModel
import com.martafode.lib.logger.api.Logger
import com.martafode.lib.ui.mvrx.BaseMviFragment
import com.martafode.lib.ui.utils.addOnTextChangedListener
import com.martafode.lib.ui.utils.fragmentLaunch
import com.martafode.lib.ui.utils.logFragmentLifecycleEvents
import kotlinx.android.synthetic.main.fragment_exchange_rates.amountField
import kotlinx.android.synthetic.main.fragment_exchange_rates.conversionResult
import kotlinx.android.synthetic.main.fragment_exchange_rates.currencyFieldLayout
import kotlinx.android.synthetic.main.fragment_exchange_rates.currencyNumberField
import kotlinx.android.synthetic.main.fragment_exchange_rates.quotesRecyclerView
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
        setupContent()
        // INPUTS - from the system
        notifyTableSelectionStartup()
        // INPUTS - from the user (like touches)
        watchCurrencySelection()
        watchAmountChanges()
    }

    // OUTPUTS - to the user (like visual effects)
    private fun setupContent() {
        val currenciesAdapter =
            ArrayAdapter(requireContext(), R.layout.item_currency, mutableListOf<String>())
        (currencyNumberField as AutoCompleteTextView).setAdapter(currenciesAdapter)
        viewModel.selectSubscribe(ExchangeRatesState::currencies) {
            currenciesAdapter.addAll(it)
            currencyFieldLayout.isEnabled = it.isNotEmpty()
        }
        val quotesAdapter = QuotesAdapter()
        quotesRecyclerView.adapter = quotesAdapter
        quotesRecyclerView.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        viewModel.selectSubscribe(ExchangeRatesState::quotes) {
            quotesAdapter.submitList(it)
        }
        viewModel.selectSubscribe(ExchangeRatesState::conversionResult) {
            conversionResult.text = String.format(getString(R.string.main_field_conversion_result_label), it)
        }
    }


    // INPUTS - from the system

    private fun notifyTableSelectionStartup() = ExchangeRatesIntent.Opened.publish()

    // INPUTS - from the user (like touches)
    private fun watchCurrencySelection() {
        (currencyNumberField as AutoCompleteTextView).setOnItemClickListener { _, _, position, _ ->
            ExchangeRatesIntent.CurrencySelected(position).publish()
        }
    }

    private fun watchAmountChanges() {
        amountField.addOnTextChangedListener {
            ExchangeRatesIntent.AmountSelected(it).publish()
        }
    }
}

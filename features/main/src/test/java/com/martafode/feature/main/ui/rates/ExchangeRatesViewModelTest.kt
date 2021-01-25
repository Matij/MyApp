package com.martafode.feature.main.ui.rates

import com.martafode.feature.main.data.currency.Currency
import com.martafode.feature.main.data.rate.ExchangeRate
import com.martafode.feature.main.data.rate.ExchangeRatesRepository
import com.martafode.lib.logger.api.Logger
import com.martafode.lib.rest.helper.ResultWrapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ExchangeRatesViewModelTest {
    private val testDispatcher = TestCoroutineDispatcher()

    private val exchangeRatesRepository: ExchangeRatesRepository = mockk(relaxed = true)
    private val logger: Logger = mockk(relaxed = true)

    private lateinit var viewModel: ExchangeRatesViewModel

    private val initialState = State()

    private val currencies = listOf(
        Currency("GBP", "Great Britain Pound"),
        Currency("AUD", "Australian Dollar"),
        Currency("USD", "US Dollar"),
        Currency("EUR", "Euro")
    )

    private val exchangeRates = listOf(
        ExchangeRate("USDGBP", "0.73"),
        ExchangeRate("USDAUD", "1.30"),
        ExchangeRate("USDUSD", "1"),
        ExchangeRate("USDEUR", "0.82")
    )

    @Before
    fun setup() {
        viewModel = ExchangeRatesViewModel(
            exchangeRatesRepository = exchangeRatesRepository,
            logger = logger,
            defaultDispatcher = testDispatcher,
            initialState = initialState,
            isDebug = true
        )
    }

    @Test
    fun `given currencies fetch succeeds, when currency is selected, update view state with correct currencyCode`() = runBlocking {
        coEvery { exchangeRatesRepository.fetchCurrencies() } returns ResultWrapper.Success(currencies)
        coEvery { exchangeRatesRepository.retrieveLiveRates() } returns ResultWrapper.Success(exchangeRates)

        viewModel.processIntents(listOf(ExchangeRatesIntent.Opened).asFlow())
        viewModel.processIntents(listOf(ExchangeRatesIntent.CurrencySelected(0)).asFlow())

        val expectedResult = currencies[0].id

        assertEquals(
            expectedResult,
            viewModel.getState().currentCurrencyCode
        )
    }

    @Test
    fun `given currencies and rates fetch succeeds, when currency selected and no amount selected, conversion result is empty`() = runBlocking {
        coEvery { exchangeRatesRepository.fetchCurrencies() } returns ResultWrapper.Success(currencies)
        coEvery { exchangeRatesRepository.retrieveLiveRates() } returns ResultWrapper.Success(exchangeRates)

        viewModel.processIntents(listOf(ExchangeRatesIntent.Opened).asFlow())
        viewModel.processIntents(listOf(ExchangeRatesIntent.CurrencySelected(0)).asFlow())

        val expectedResult = ""

        assertEquals(
            expectedResult,
            viewModel.getState().conversionResult
        )
    }

    @Test
    fun `given currencies fetch succeeds, when currency is selected and amount input, update view state with conversion result`() = runBlocking {
        coEvery { exchangeRatesRepository.fetchCurrencies() } returns ResultWrapper.Success(currencies)
        coEvery { exchangeRatesRepository.retrieveLiveRates() } returns ResultWrapper.Success(exchangeRates)

        val inputAmount = "15"
        viewModel.processIntents(listOf(ExchangeRatesIntent.Opened).asFlow())
        viewModel.processIntents(listOf(ExchangeRatesIntent.CurrencySelected(0)).asFlow())
        viewModel.processIntents(listOf(ExchangeRatesIntent.AmountSelected(inputAmount)).asFlow())

        val currencyRate = exchangeRates.first { it.id == "USD${currencies[0].id}" }.value.toDouble()
        val expectedResult = inputAmount.toDouble().times(currencyRate)

        assertEquals(
            expectedResult.toString(),
            viewModel.getState().conversionResult
        )
    }

    @Test
    fun `given currencies fetch fails, update view state with error message`() = runBlocking {
        val error = Throwable("error fetching currencies")
        coEvery { exchangeRatesRepository.fetchCurrencies() } returns ResultWrapper.GenericError(origin = error)

        viewModel.processIntents(listOf(ExchangeRatesIntent.Opened).asFlow())

        assertEquals(
            initialState.copy(errorMessage = "ERROR: $error"),
            viewModel.getState()
        )
    }

    @Test
    fun `given quotes fetch fails, update view state with error message`() = runBlocking {
        val error = Throwable("error fetching currencies")
        coEvery { exchangeRatesRepository.retrieveLiveRates() } returns ResultWrapper.GenericError(origin = error)

        viewModel.processIntents(listOf(ExchangeRatesIntent.Opened).asFlow())

        assertEquals(
            initialState.copy(errorMessage = "ERROR: $error"),
            viewModel.getState()
        )
    }
}

private fun List<ExchangeRatesIntent>.asFlow(): Flow<ExchangeRatesIntent> = flow {
    forEach { value ->
        emit(value)
    }
}

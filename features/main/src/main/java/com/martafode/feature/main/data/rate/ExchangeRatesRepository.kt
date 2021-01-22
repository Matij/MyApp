package com.martafode.feature.main.data.rate

import com.martafode.feature.main.data.currency.Currency
import com.martafode.feature.main.data.currency.toDataModel
import com.martafode.feature.main.data.remote.Api
import com.martafode.feature.main.di.MAIN_ACCESS_KEY
import com.martafode.lib.rest.helper.NetworkHelper
import com.martafode.lib.rest.helper.ResultWrapper
import javax.inject.Inject
import javax.inject.Named

class ExchangeRatesRepository @Inject constructor(
    @Named(MAIN_ACCESS_KEY) private val accessKey: String,
    private val api: Api,
    private val networkHelper: NetworkHelper,
) {
    suspend fun fetchCurrencies(): ResultWrapper<List<Currency>> =
        networkHelper.safeApiCall {
            api.fetchCurrencies(accessKey).toDataModel()
        }

    suspend fun fetchLiveRates(currencyCode: String, currencies: String): ResultWrapper<List<ExchangeRate>> =
        networkHelper.safeApiCall {
            api.fetchLiveExchangeRates(accessKey, currencyCode, currencies).toDataModel()
        }
}

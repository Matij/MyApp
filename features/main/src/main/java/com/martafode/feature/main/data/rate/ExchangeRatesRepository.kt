package com.martafode.feature.main.data.rate

import com.martafode.feature.main.data.currency.Currency
import com.martafode.feature.main.data.currency.toDataModel
import com.martafode.feature.main.data.local.AppPreferencesHelper
import com.martafode.feature.main.data.local.AppPreferencesHelper.Companion.LAST_DATA_FETCH_TIMESTAMP
import com.martafode.feature.main.data.local.DataPreferencesHelper
import com.martafode.feature.main.data.remote.Api
import com.martafode.feature.main.di.MAIN_ACCESS_KEY
import com.martafode.lib.rest.helper.NetworkHelper
import com.martafode.lib.rest.helper.ResultWrapper
import javax.inject.Inject
import javax.inject.Named

const val MINUTES_30 = 30 * 60 * 1000L // 30 minutes

class ExchangeRatesRepository @Inject constructor(
    @Named(MAIN_ACCESS_KEY) private val accessKey: String,
    private val api: Api,
    private val appPreferencesHelper: AppPreferencesHelper,
    private val dataPreferencesHelper: DataPreferencesHelper,
    private val networkHelper: NetworkHelper,
) {
    suspend fun fetchCurrencies(): ResultWrapper<List<Currency>> =
        networkHelper.safeApiCall {
            api.fetchCurrencies(accessKey).toDataModel()
        }

    suspend fun retrieveLiveRates(): ResultWrapper<List<ExchangeRate>> {
        val localData = dataPreferencesHelper.retrieveAllData()
        return if (localData.isNotEmpty() && MINUTES_30.hasPassedSinceLastFetchTime().not()) {
            ResultWrapper.Success(localData.mapDataToModel())
        } else {
            appPreferencesHelper.write(LAST_DATA_FETCH_TIMESTAMP, System.currentTimeMillis())
            fetchLiveRates()
        }
    }

    private suspend fun fetchLiveRates(currencyCode: String = "", currencies: String = ""): ResultWrapper<List<ExchangeRate>> =
        networkHelper.safeApiCall {
            val data = api.fetchLiveExchangeRates(accessKey, currencyCode, currencies).toDataModel()
            data.persistData()
        }

    private fun List<ExchangeRate>.persistData(): List<ExchangeRate> {
        forEach {
            dataPreferencesHelper.write(it.id, it.value)
        }
        return this
    }

    private fun Map<String, *>.mapDataToModel(): List<ExchangeRate> {
        return this.map {
            ExchangeRate(it.key, it.value as String)
        }
    }

    private fun Long.hasPassedSinceLastFetchTime(): Boolean {
        val lastFetchTimestamp = appPreferencesHelper.getLong(LAST_DATA_FETCH_TIMESTAMP) ?: 0L
        return System.currentTimeMillis() - lastFetchTimestamp >= this
    }
}

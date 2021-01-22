package com.martafode.feature.main.data.rate

import com.martafode.feature.main.data.remote.Api

data class ExchangeRate(
    val id: String,
    val value: String,
)

fun Api.ApiExchangeRateData.toDataModel(): List<ExchangeRate> {
    return quotes.map { ExchangeRate(it.key, it.value.toString()) }
}

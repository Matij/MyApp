package com.martafode.feature.main.data.currency

import com.martafode.feature.main.data.remote.Api

data class Currency(
    val id: String,
    val name: String,
)

fun Api.ApiCurrencyData.toDataModel(): List<Currency> {
    return currencies.map { Currency(it.key, it.value) }
}

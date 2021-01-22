package com.martafode.feature.main.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @JsonClass(generateAdapter = true)
    data class ApiExchangeRateData(
        @Json(name = "source") val source: String,
        @Json(name = "timestamp") val timestamp: String,
        @Json(name = "quotes") val quotes: Map<String, Double>,
        @Json(name = "terms") val terms: String,
        @Json(name = "privacy") val privacy: String,
        @Json(name = "success") val success: Boolean,
    )

    @GET("live")
    suspend fun fetchLiveExchangeRates(
        @Query("access_key") accessKey: String,
        @Query("source") currencyCode: String,
        @Query("currencies") currencies: String,
    ): ApiExchangeRateData

    @JsonClass(generateAdapter = true)
    data class ApiCurrencyData(
        @Json(name = "success") val success: Boolean,
        @Json(name = "timestamp") val timestamp: String?,
        @Json(name = "currencies") val currencies: Map<String, String>,
        @Json(name = "terms") val terms: String,
        @Json(name = "privacy") val privacy: String,
    )

    @GET("list")
    suspend fun fetchCurrencies(
        @Query("access_key") accessKey: String,
    ): ApiCurrencyData
}

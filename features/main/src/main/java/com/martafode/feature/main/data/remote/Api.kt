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
    data class ApiQuery(
        @Json(name = "from") val from: String,
        @Json(name = "to") val to: String,
        @Json(name = "amount") val amount: String,
    )

    @JsonClass(generateAdapter = true)
    data class ApiInfo(
        @Json(name = "timestamp") val from: Long,
        @Json(name = "quote") val to: Double,
    )

    @JsonClass(generateAdapter = true)
    data class ApiConversion(
        @Json(name = "source") val source: String,
        @Json(name = "terms") val terms: String,
        @Json(name = "privacy") val privacy: String,
        @Json(name = "query") val query: ApiQuery,
        @Json(name = "info") val info: ApiInfo,
        @Json(name = "result") val result: String,
    )

    @GET("convert")
    suspend fun fetchAmountConversion(
        @Query("access_key") accessKey: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: String,
    ): ApiConversion

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

package com.martafode.lib.rest.helper

import com.martafode.lib.concurrency.api.AppCoroutineDispatchers
import com.martafode.lib.di.ApplicationScoped
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import java.io.IOException
import java.lang.ClassCastException
import javax.inject.Inject
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@ApplicationScoped
class NetworkHelper @Inject constructor(
    private val moshi: Moshi,
    private val dispatchers: AppCoroutineDispatchers
) {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): ResultWrapper<T> {
        return withContext(dispatchers.io) {
            try {
                val result = apiCall.invoke()
                ResultWrapper.Success(result)
            } catch (e: IOException) {
                ResultWrapper.NetworkError(e, e.message)
            } catch (e: HttpException) {
                val code = e.code()
                val errorResponse = convertErrorBody(e)
                ResultWrapper.GenericError(e, code, errorResponse)
            } catch (e: IllegalStateException) {
                ResultWrapper.GenericError(e)
            } catch (e: JsonDataException) {
                ResultWrapper.ParsingError(e)
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.string()?.let {
                val moshiAdapter = moshi.adapter(ErrorResponse::class.java)
                return moshiAdapter.fromJson(it)
            }
        } catch (e: JsonDataException) {
            null
        } catch (e: IOException) {
            null
        } catch (e: ClassCastException) {
            null
        }
    }
}

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "error") val error: ApiError
)

@JsonClass(generateAdapter = true)
data class ApiError(
    @Json(name = "code") val code: Int,
    @Json(name = "type") val type: String,
    @Json(name = "info") val info: String
)

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()

    data class GenericError(
        val origin: Throwable,
        val code: Int? = null,
        val error: ErrorResponse? = null
    ) : ResultWrapper<Nothing>()

    data class NetworkError(val origin: Throwable, val message: String?) : ResultWrapper<Nothing>()

    data class ParsingError(val origin: Throwable) : ResultWrapper<Nothing>()
}

package com.hijazi.challenge.data.remote

import android.text.TextUtils
import com.google.gson.Gson
import retrofit2.adapter.rxjava.HttpException

import java.io.IOException
import java.util.Objects

import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class NetworkError internal constructor(val error: Throwable?) : Throwable(error) {
    private val DEFAULT_ERROR_MESSAGE = "Something went wrong! Please try again."
    private val NETWORK_ERROR_MESSAGE = "No internet connection or Internal server Error!"
    private val ERROR_MESSAGE_HEADER = "Error-Message"

    val isAuthFailure: Boolean
        get() = error is HttpException && error.code() == HTTP_UNAUTHORIZED

    val isResponseNull: Boolean
        get() = error is HttpException && error.response() == null

    val appErrorMessage: String?
        get() {
            if (this.error is IOException) return NETWORK_ERROR_MESSAGE

            if (this.error !is HttpException) return DEFAULT_ERROR_MESSAGE
            val response = this.error.response()

            val status = getJsonStringFromResponse(response)
            if (!TextUtils.isEmpty(status)) return status

            val headers = response.headers().toMultimap()
            return if (headers.containsKey(ERROR_MESSAGE_HEADER)) Objects.requireNonNull<List<String>>(
                headers[ERROR_MESSAGE_HEADER]
            )[0] else DEFAULT_ERROR_MESSAGE

        }

    private fun getJsonStringFromResponse(response: retrofit2.Response<*>): String? {
        return try {
            assert(response.errorBody() != null)
            val jsonString = response.errorBody()!!.string()
            val errorResponse = Gson().fromJson(jsonString, Response::class.java)
            errorResponse.errorInfo
        } catch (e: Exception) {
            null
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val that = other as NetworkError?

        return error == that!!.error

    }

    override fun hashCode(): Int {
        return error?.hashCode() ?: 0
    }

}

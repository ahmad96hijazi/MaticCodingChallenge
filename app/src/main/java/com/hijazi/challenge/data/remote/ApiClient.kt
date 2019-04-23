package com.hijazi.challenge.data.remote

import com.hijazi.challenge.BuildConfig

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = BuildConfig.BASEURL
    private var retrofit: Retrofit? = null

    private val logging: OkHttpClient
        get() {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
            builder.interceptors().add(logging)
            builder.readTimeout(60, TimeUnit.SECONDS)
            builder.connectTimeout(60, TimeUnit.SECONDS)
            builder.writeTimeout(120, TimeUnit.SECONDS)
            builder.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .build()

                chain.proceed(request)
            }

            return builder.build()
        }

    val client: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(NullOnEmptyConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(logging)
                        .build()
            }
            return retrofit
        }
}

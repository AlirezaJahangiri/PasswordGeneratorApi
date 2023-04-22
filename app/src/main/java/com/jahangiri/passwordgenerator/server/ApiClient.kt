package com.jahangiri.passwordgenerator.server

import com.jahangiri.passwordgenerator.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    private lateinit var retrofit: Retrofit

    private val client = OkHttpClient.Builder()
        .connectTimeout(Constants.NETWORK_TIME, TimeUnit.SECONDS)
        .writeTimeout(Constants.NETWORK_TIME, TimeUnit.SECONDS)
        .readTimeout(Constants.NETWORK_TIME, TimeUnit.SECONDS)
        .build()

    fun getClient(): Retrofit {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

}
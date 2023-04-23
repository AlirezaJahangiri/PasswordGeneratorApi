package com.jahangiri.passwordgenerator.server

import com.jahangiri.passwordgenerator.model.ResponseRandomFacts
import com.jahangiri.passwordgenerator.model.ResponseRandomPassword
import com.jahangiri.passwordgenerator.model.ResponseWorldTime
import com.jahangiri.passwordgenerator.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiServices {


    @Headers("X-Api-Key: ${Constants.XAPI_KEY}")
    @GET("facts")
    fun getRandomFacts(): Call<MutableList<ResponseRandomFacts.ResponseRandomFactsItem>>

    @Headers("X-Api-Key: ${Constants.XAPI_KEY}")
    @GET("worldtime")
    fun getWorldTime(@Query("city") city: String): Call<ResponseWorldTime>

    @Headers("X-Api-Key: ${Constants.XAPI_KEY}")
    @GET("passwordgenerator")
    fun getRandomPassword(
        @Query("length") length: Int,
        @Query("exclude_numbers") exclude_numbers: String,
        @Query("exclude_special_chars") exclude_special_chars: String
    ): Call<ResponseRandomPassword>
}
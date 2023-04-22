package com.jahangiri.passwordgenerator.server

import com.jahangiri.passwordgenerator.model.ResponseRandomFacts
import com.jahangiri.passwordgenerator.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiServices {


    @Headers("X-Api-Key: ${Constants.XAPI_KEY}")
    @GET("facts")
    fun getRandomFacts(): Call<MutableList<ResponseRandomFacts.ResponseRandomFactsItem>>
}
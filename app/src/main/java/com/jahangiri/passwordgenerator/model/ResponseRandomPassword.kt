package com.jahangiri.passwordgenerator.model


import com.google.gson.annotations.SerializedName

data class ResponseRandomPassword(
    @SerializedName("random_password")
    val randomPassword: String? // GFIS8multkm0
)
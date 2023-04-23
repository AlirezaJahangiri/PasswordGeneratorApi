package com.jahangiri.passwordgenerator.model


import com.google.gson.annotations.SerializedName

data class ResponseWorldTime(
    @SerializedName("date")
    val date: String?, // 2023-04-22
    @SerializedName("datetime")
    val datetime: String?, // 2023-04-22 12:51:16
    @SerializedName("day")
    val day: String?, // 22
    @SerializedName("day_of_week")
    val dayOfWeek: String?, // Saturday
    @SerializedName("hour")
    val hour: String?, // 12
    @SerializedName("minute")
    val minute: String?, // 51
    @SerializedName("month")
    val month: String?, // 04
    @SerializedName("second")
    val second: String?, // 16
    @SerializedName("timezone")
    val timezone: String?, // Asia/Tehran
    @SerializedName("year")
    val year: String? // 2023
)
package com.jahangiri.passwordgenerator.model


import com.google.gson.annotations.SerializedName

class ResponseRandomFacts : ArrayList<ResponseRandomFacts.ResponseRandomFactsItem>() {
    data class ResponseRandomFactsItem(
        @SerializedName("fact")
        val fact: String? // In 1970, Chip maker Intel purchased a pear orchard to build their corporate headquarters on
    )
}
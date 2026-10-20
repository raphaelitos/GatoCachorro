package com.example.gatocachorro.data.remote.entity

import com.google.gson.annotations.SerializedName

/**
 * Resposta de https://api.jsongpt.com/json?prompt=...&facts=array of bird facts
 * {"facts": ["...", "..."]}
 */
class BirdFactEntity {
    @SerializedName("facts")
    var facts: List<String> = emptyList()
}

package com.example.gatocachorro.data.remote.entity

import com.google.gson.annotations.SerializedName

/**
 * Resposta de https://catfact.ninja/fact
 * {"fact": "...", "length": 180}
 */
class CatFactEntity {
    @SerializedName("fact")
    var fact: String = ""

    @SerializedName("length")
    var length: Int = 0
}

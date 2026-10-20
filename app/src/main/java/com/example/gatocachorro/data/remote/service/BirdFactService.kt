package com.example.gatocachorro.data.remote.service

import com.example.gatocachorro.data.remote.entity.BirdFactEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BirdFactService {
    /** @Query acrescenta ?prompt=...&facts=... na URL, no formato documentado pelo jsongpt. */
    @GET("json")
    fun getFacts(
        @Query("prompt") prompt: String,
        @Query("facts") formato: String
    ): Call<BirdFactEntity>
}

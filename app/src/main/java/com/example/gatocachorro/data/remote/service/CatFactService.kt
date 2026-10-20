package com.example.gatocachorro.data.remote.service

import com.example.gatocachorro.data.remote.entity.CatFactEntity
import retrofit2.Call
import retrofit2.http.GET

interface CatFactService {
    @GET("fact")
    fun getFact(): Call<CatFactEntity>
}

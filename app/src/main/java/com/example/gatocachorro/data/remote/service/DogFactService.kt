package com.example.gatocachorro.data.remote.service

import com.example.gatocachorro.data.remote.entity.DogFactEntity
import retrofit2.Call
import retrofit2.http.GET

interface DogFactService {
    @GET("api/v2/facts")
    fun getFact(): Call<DogFactEntity>
}

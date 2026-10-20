package com.example.gatocachorro.data.remote

import com.example.gatocachorro.data.remote.service.BirdFactService
import com.example.gatocachorro.data.remote.service.CatFactService
import com.example.gatocachorro.data.remote.service.DogFactService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente do Retrofit (Singleton).
 * Cada API tem uma URL base diferente, entao guardamos uma instancia por URL base
 * em vez de uma unica INSTANCE como no exemplo do JSONPlaceholder.
 */
class ClientRetrofit {
    companion object {
        private const val CAT_FACT_URL = "https://catfact.ninja/"
        private const val DOG_FACT_URL = "https://dogapi.dog/"
        private const val BIRD_FACT_URL = "https://api.jsongpt.com/"

        private val INSTANCES = mutableMapOf<String, Retrofit>()

        private fun getClientInstance(baseUrl: String): Retrofit {
            val http = OkHttpClient.Builder()
            if (!INSTANCES.containsKey(baseUrl)) {
                INSTANCES[baseUrl] = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(http.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCES.getValue(baseUrl)
        }

        fun <S> createService(baseUrl: String, className: Class<S>): S {
            return getClientInstance(baseUrl).create(className)
        }

        fun createCatFactService(): CatFactService =
            createService(CAT_FACT_URL, CatFactService::class.java)

        fun createDogFactService(): DogFactService =
            createService(DOG_FACT_URL, DogFactService::class.java)

        fun createBirdFactService(): BirdFactService =
            createService(BIRD_FACT_URL, BirdFactService::class.java)
    }
}

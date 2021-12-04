package com.nahlasamir244.mvi_architecture_demo.data.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// build endpoint URL and consume REST services
object RetrofitBuilder {

    private const val BASE_URL = "https://5e510330f2c0d300147c034c.mockapi.io/"

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()


    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}
package com.nahlasamir244.mvi_architecture_demo.data.api

import com.nahlasamir244.mvi_architecture_demo.data.model.User
import retrofit2.http.GET

interface ApiService {
//specify HTTP methods to communicate to the API.
    @GET("users")
    suspend fun getUsers(): List<User>
}
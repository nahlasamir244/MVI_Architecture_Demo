package com.nahlasamir244.mvi_architecture_demo.data.api

import com.nahlasamir244.mvi_architecture_demo.data.model.User

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}
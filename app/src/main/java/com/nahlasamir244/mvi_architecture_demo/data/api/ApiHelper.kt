package com.nahlasamir244.mvi_architecture_demo.data.api

import com.nahlasamir244.mvi_architecture_demo.data.model.User

//We have used suspend keyword to support Coroutines
//so that we can call it from a Coroutine or another suspend function
interface ApiHelper {

    suspend fun getUsers(): List<User>

}
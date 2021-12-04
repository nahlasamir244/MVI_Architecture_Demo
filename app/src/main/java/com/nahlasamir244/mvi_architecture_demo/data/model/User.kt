package com.nahlasamir244.mvi_architecture_demo.data.model

import com.squareup.moshi.Json

//the response model
data class User(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "email")
    val email: String = "",
    @Json(name = "avatar")
    val avatar: String = ""
)

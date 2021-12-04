package com.nahlasamir244.mvi_architecture_demo.ui.main.viewstate

import com.nahlasamir244.mvi_architecture_demo.data.model.User

//we are defining the states Idle, loading, users, error.
// Each state can be loaded on to the view by the user intents.
sealed class MainState {

    object Idle : MainState()
    object Loading : MainState()
    data class Users(val users: List<User>) : MainState()
    data class Error(val error: String?) : MainState()

}
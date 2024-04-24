package com.example.triviagame.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyHighScoreRequest (
    @SerialName("name")
    val name: String,

    @SerialName("score")
    val highScore: Int,

    @SerialName("date")
    val date: String,
)

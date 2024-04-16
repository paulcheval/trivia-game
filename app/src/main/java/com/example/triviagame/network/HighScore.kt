package com.example.triviagame.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HighScore (
    @SerialName("name")
    val name: String,

    @SerialName("score")
    val highScore: Int,

    @SerialName("date")
    val date: String,

    @SerialName("ranking")
    val ranking: Int
)

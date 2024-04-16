package com.example.triviagame.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HighScores(
    @SerialName("highScores")
    val highScores : List<HighScore>,

)
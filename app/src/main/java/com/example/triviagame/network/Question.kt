package com.example.triviagame.network


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Question(
    @SerialName("text")
    val text: String
)
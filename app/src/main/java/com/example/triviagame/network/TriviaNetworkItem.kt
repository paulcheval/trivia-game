package com.example.triviagame.network


import com.example.triviagame.network.Question
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TriviaNetworkItem(
    @SerialName("category")
    val category: String,
    @SerialName("correctAnswer")
    val correctAnswer: String,
    @SerialName("difficulty")
    val difficulty: String,
    @SerialName("id")
    val id: String,
    @SerialName("incorrectAnswers")
    val incorrectAnswers: List<String>,
    @SerialName("isNiche")
    val isNiche: Boolean,
    @SerialName("question")
    val question: Question,
    @SerialName("regions")
    val regions: List<String>,
    @SerialName("tags")
    val tags: List<String>,
    @SerialName("type")
    val type: String
)
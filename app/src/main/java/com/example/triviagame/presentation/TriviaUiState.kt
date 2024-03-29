package com.example.triviagame.presentation

import com.example.triviagame.network.TriviaNetworkItem

data class TriviaUiState(
    val currentScore: Int = 0,
    val highScore: Int = 0,
    val currentQuestion: Int = 0,
    val questions: List<TriviaNetworkItem> = listOf(),
    val selectedAnswer: String = "",
    val attempts: Int = 0
) {
}
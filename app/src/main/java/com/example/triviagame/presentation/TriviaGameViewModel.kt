package com.example.triviagame.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviagame.network.TriviaNetworkItem

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TriviaGameViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow((TriviaUiState()))
    val uiState: StateFlow<TriviaUiState> = _uiState.asStateFlow()

    init {
        Log.d("viewmodel - init", uiState.toString())
        loadData()
    }
    private fun loadData() {
        Log.d("viewmodel-loaddata", "made it to loaddata")
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("viewmodel-loaddata", "calling api")
            val triviaQuestions = retrieveListOfTriviaQuestionsFromNetwork()
            Log.d("viewmodel-loadData", "Retrieved trivia ${triviaQuestions}")
            triviaQuestions.forEach {
                Log.d("ViewModel - load", "$it")
            }
            _uiState.update { currentState ->
                Log.d("viewmodel-loaddata", "seeting state")
                currentState.copy(
                    questions = triviaQuestions
                )
            }

        }
    }

    fun reloadData() {
        Log.d("Viewmodel-reload", "Reloading the data")
        resetScore()
        resetAttempts()
        loadData()
        _uiState.update { currentState->
            currentState.copy(
                currentQuestion = 0
            )
        }
        Log.d("Viewmodel-reload", "Done reloading")
    }
    private suspend fun retrieveListOfTriviaQuestionsFromNetwork(): List<TriviaNetworkItem> {
        Log.d("viewmodel-retrieve", "made it to retrieve")
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("application", "json"))
            }
        }
        Log.d("viewmodel-retrieve", "got a client ${client.toString()}")
        val httpResponse = client.get("https://the-trivia-api.com/v2/questions")
        Log.d("viewmodel-retrieve", "httpResponse as trivia network ${httpResponse.toString()}")
        val response: List<TriviaNetworkItem> =
            client.get("https://the-trivia-api.com/v2/questions").body()
        Log.d("retrieve", response.toString())
        return response
    }

    fun getListOfAnswers(correctAnswer: String, incorrectAnswers: List<String>): List<String> {
        var allAnswers = mutableListOf<String>()
        allAnswers.add(correctAnswer)
        allAnswers.addAll(incorrectAnswers)
        return allAnswers.shuffled()
    }

    fun isAnswerCorrect(submittedAnswer: String, correctAnswer: String): Boolean {
        val isCorrect = submittedAnswer.equals(correctAnswer, ignoreCase = true)
        Log.d("Viewmodel-iscorrect", "$isCorrect")
       return isCorrect
    }

    fun getNextQuestion() {
        val currentQuestion = uiState.value.currentQuestion
        if (currentQuestion == uiState.value.questions.size -1) {
            _uiState.update {currentState->
                currentState.copy(
                    currentQuestion = -1,
                    attempts = 0

                )

            }
        }
        else {
            _uiState.update { currentState->
                currentState.copy(
                    currentQuestion = currentQuestion + 1,
                    attempts = 0
                )
            }
        }
    }

    fun incrementScore(question: TriviaNetworkItem) {
        val currentScore = uiState.value.currentScore
        val increment = questionValue(question)

        _uiState.update { currentState ->
            currentState.copy(
                currentScore + increment
            )
        }
    }

    fun incrementAttempts() {
        var attempts = uiState.value.attempts
        if (attempts ==3 ) {
            attempts = 3
        } else {
            attempts += 1
        }
        _uiState.update { currentState ->
            currentState.copy(
                attempts = attempts
            )
        }
    }
    fun resetAttempts() {
        _uiState.update { currentState ->
            currentState.copy(
                attempts = 0
            )
        }
    }

    fun questionValue(question: TriviaNetworkItem): Int {
        return Difficulty.valueOf(question.difficulty).scoring[uiState.value.attempts]
    }

    private fun resetScore() {
        _uiState.update { currentState->
            currentState.copy(
                currentScore = 0
            )
        }
    }
}
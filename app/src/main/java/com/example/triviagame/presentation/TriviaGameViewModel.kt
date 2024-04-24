package com.example.triviagame.presentation

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviagame.network.HighScoreService
import com.example.triviagame.network.HighScores
import com.example.triviagame.network.MyHighScoreRequest
import com.example.triviagame.network.NetworkQandAService
import com.example.triviagame.network.TriviaNetworkItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class TriviaGameViewModel (
     application: Application,

): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow((TriviaUiState()))
    val uiState: StateFlow<TriviaUiState> = _uiState.asStateFlow()
    private val context = application

    init {
        loadData()
    }
    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            retriveAndLoadQuestionsAndAnswers()
        }
    }



    public suspend fun retriveAndLoadQuestionsAndAnswers() {
        val triviaQuestions = retrieveListOfTriviaQuestionsFromNetwork()
        //Log.d("TriviaGameViewModel-loadData", "Retrieved trivia $triviaQuestions")

        _uiState.update { currentState ->
            currentState.copy(
                questions = triviaQuestions,
                highScore = getHighScore(),
                highScoreDate = getHighScoreDate()
            )
        }
    }

    fun reloadData() {
        resetScore()
        resetAttempts()
        loadData()
        _uiState.update { currentState ->
            currentState.copy(
                currentQuestion = 0
            )
        }
    }

    suspend fun retrieveListOfTriviaQuestionsFromNetwork() : List<TriviaNetworkItem>{
        return NetworkQandAService().retrieveListOfTriviaQuestionsFromNetwork()
    }



    fun resetData() {
        _uiState.update { currentState ->
            currentState.copy(
                attempts = 0,
                currentQuestion = -1,
                currentScore = 0,
                questions = listOf(),
                selectedAnswer = "",
                highScore = 0
            )

        }
    }


    fun getListOfAnswers(correctAnswer: String, incorrectAnswers: List<String>): List<String> {
        val allAnswers = mutableListOf<String>()
        allAnswers.add(correctAnswer)
        allAnswers.addAll(incorrectAnswers)
        return allAnswers.shuffled()
    }

    fun isAnswerCorrect(submittedAnswer: String, correctAnswer: String): Boolean {
        return submittedAnswer.equals(correctAnswer, ignoreCase = true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNextQuestion() {
        val currentQuestion = uiState.value.currentQuestion
        if (currentQuestion == uiState.value.questions.size - 1) {
            _uiState.update { currentState ->
                currentState.copy(
                    currentQuestion = -1,
                    attempts = 0
                )
            }
            maybeUpdateHighScore()
        } else {
            _uiState.update { currentState ->
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
                currentScore =currentScore + increment
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun maybeUpdateHighScore() {
        val sharedPreferences = context.getSharedPreferences(
            SHARED_PREF_LOC,
            Context.MODE_PRIVATE)
        if (isNewHighScore()) {
            viewModelScope.launch(Dispatchers.IO) {
                sharedPreferences.edit()
                    .putInt(HIGH_SCORE_PREF_KEY, uiState.value.highScore)
                    .apply()
                sharedPreferences.edit()
                    .putString(HIGH_SCORE_DATE_PREF_KEY, determineCurrentDate())
                    .apply()
                postHighScoreToNetwork()
            }

            _uiState.update {currentState ->
                currentState.copy(
                    highScore = uiState.value.currentScore,
                    highScoreDate = uiState.value.highScoreDate

                )

            }
        }

    }

    private fun getHighScore(): Int {
        val sharedPreferences = context.getSharedPreferences(
            SHARED_PREF_LOC,
            Context.MODE_PRIVATE)
        return  sharedPreferences.getInt(HIGH_SCORE_PREF_KEY, 0)
    }

    private fun getHighScoreDate(): String {
        val sharedPreferences = context.getSharedPreferences(
            SHARED_PREF_LOC,
            Context.MODE_PRIVATE)
        return  sharedPreferences.getString(    HIGH_SCORE_DATE_PREF_KEY,"") ?: ""
    }

    private fun isNewHighScore(): Boolean {
        return uiState.value.currentScore > uiState.value.highScore
    }

    public fun retieveHighScores() {
        viewModelScope.launch(Dispatchers.IO) {
            val scores = retrieveHighScoresFromNetwork()
            val sortedScores = scores.highScores.sortedBy { it.ranking }
            _uiState.update { currentState ->
                currentState.copy(
                    highScores = sortedScores
                )
            }
        }
    }
    suspend fun retrieveHighScoresFromNetwork() : HighScores{
        return HighScoreService().retrieveListOfHighScoresFromNetwork()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun postHighScoreToNetwork() {
        HighScoreService().postHighScoreToNetwork(
            MyHighScoreRequest(
                name = "Bobo, the dog-faced boy",
                highScore = uiState.value.currentScore,
                date = determineCurrentDate())
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun determineCurrentDate(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return current.format(formatter)
    }

    companion object {
        const val SHARED_PREF_LOC =  "TriviaGameScores.prefs"
        const val TRIVIA_QUESTIONS_URL = "https://the-trivia-api.com/v2/questions"
        const val HIGH_SCORE_URL = "http://10.0.0.187:8080/trivia-high-score/scores"
        const val MYHIGH_SCORE_URL = "http://10.0.0.187:8080/trivia-high-score/highscore"
        const val HIGH_SCORE_PREF_KEY = "highScore"
        const val HIGH_SCORE_DATE_PREF_KEY = "highscoredate"


    }
}
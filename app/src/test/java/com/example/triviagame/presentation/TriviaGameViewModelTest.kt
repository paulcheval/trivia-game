package com.example.triviagame.presentation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.triviagame.network.NetworkQandAService
import com.example.triviagame.network.Question
import com.example.triviagame.network.TriviaNetworkItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.junit.Assert.assertEquals
import org.mockito.Mock

class TriviaGameViewModelTest {

    @Mock
    private lateinit var viewModel: TriviaGameViewModel
    private val application: Application = Mockito.mock(Application::class.java)
    private val sharedPreferences: SharedPreferences = Mockito.mock(SharedPreferences::class.java)
    private val editor: SharedPreferences.Editor = Mockito.mock(SharedPreferences.Editor::class.java)
    private val networkQandAService: NetworkQandAService = Mockito.mock(NetworkQandAService::class.java)

    @Before
    @kotlinx.coroutines.ExperimentalCoroutinesApi
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        Mockito.`when`(application.getSharedPreferences(TriviaGameViewModel.SHARED_PREF_LOC, Context.MODE_PRIVATE)).thenReturn(sharedPreferences)
        Mockito.`when`(sharedPreferences.edit()).thenReturn(editor)
        viewModel = TriviaGameViewModel(application, networkQandAService)
    }

    @Test
    fun testIsAnswerCorrect() {
        val correctAnswer = "correct"
        val incorrectAnswer = "incorrect"

        assertEquals(true, viewModel.isAnswerCorrect(correctAnswer, correctAnswer))
        assertEquals(false, viewModel.isAnswerCorrect(correctAnswer, incorrectAnswer))
    }

    @Test
    fun testGetListOfAnswers() {
        val correctAnswer = "correct"
        val incorrectAnswers = listOf("incorrect1", "incorrect2", "incorrect3")

        val answers = viewModel.getListOfAnswers(correctAnswer, incorrectAnswers)

        assertEquals(4, answers.size)
        assertEquals(true, answers.contains(correctAnswer))
    }

    @Test
    fun testReloadData() = runBlocking {
        val triviaQuestions = listOf(
            TriviaNetworkItem(category = "category1",
                question = Question("question1"),
                type = "type",
                tags = emptyList(),
                regions = listOf("region"),
                isNiche = false,
                id = "0",
                difficulty = "easy",
                correctAnswer = "correct1",
                incorrectAnswers = listOf("incorrect1", "incorrect2", "incorrect3")),
            TriviaNetworkItem(category = "category2",
                question = Question("question2"),
                type = "type",
                tags = emptyList(),
                regions = listOf("region"),
                isNiche = false,
                id = "1",
                difficulty = "easy",
                correctAnswer = "correct2",
                incorrectAnswers = listOf("incorrect1", "incorrect2", "incorrect3")),
            TriviaNetworkItem(category = "category3",
                question = Question("question3"),
                type = "type",
                tags = emptyList(),
                regions = listOf("region"),
                isNiche = false,
                id = "2",
                difficulty = "easy",
                correctAnswer = "correct3",
                incorrectAnswers = listOf("incorrect1", "incorrect2", "incorrect3"))
        )



        // Mock the network call
        Mockito.`when`(networkQandAService.retrieveListOfTriviaQuestionsFromNetwork()).thenReturn(triviaQuestions)

        // Create the ViewModel after defining the behavior of the mock
        viewModel = TriviaGameViewModel(application, networkQandAService)

        viewModel.retriveAndLoadQuestionsAndAnswers()

        val uiState = viewModel.uiState.value

        assertEquals(0, uiState.currentQuestion)
        assertEquals(triviaQuestions, uiState.questions)
    }

    // Add more tests for other public methods
}
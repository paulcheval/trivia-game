package com.example.triviagame.network

import com.example.triviagame.presentation.TriviaGameViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

class HighScoreService {

    private fun httpClient(): HttpClient {
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("application", "json"))
            }
        }
        return client
    }

    public suspend fun retrieveListOfHighScoresFromNetwork(): HighScores {
        return httpClient().get(TriviaGameViewModel.HIGH_SCORE_URL).body<HighScores>()
    }

    public suspend fun postHighScoreToNetwork(highScore: MyHighScoreRequest) {
        httpClient().post(TriviaGameViewModel.MYHIGH_SCORE_URL) {
            contentType(ContentType.Application.Json)
            setBody(highScore)
        }
    }


}

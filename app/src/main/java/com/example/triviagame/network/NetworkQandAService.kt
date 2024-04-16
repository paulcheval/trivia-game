package com.example.triviagame.network

import com.example.triviagame.presentation.TriviaGameViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json

class NetworkQandAService {
    public suspend fun retrieveListOfTriviaQuestionsFromNetwork(): List<TriviaNetworkItem> {
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("application", "json"))
            }
        }

        return client.get(TriviaGameViewModel.TRIVIA_QUESTIONS_URL).body<List<TriviaNetworkItem>>()
    }
}

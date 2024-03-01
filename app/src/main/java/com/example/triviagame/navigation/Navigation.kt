package com.example.triviagame.navigation

interface Destinations {
    val route: String
}

object Home: Destinations {
    override val route = "Home"
}

object TriviaInfo: Destinations {
    override val route = "TriviaInfo"
}

object Instructions: Destinations {
    override val route = "Instructions"
}
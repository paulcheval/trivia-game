package com.example.triviagame.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.triviagame.presentation.HighScoresScreen
import com.example.triviagame.presentation.HomeScreen
import com.example.triviagame.presentation.InstructionScreen
import com.example.triviagame.presentation.TriviaInfo

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
    ) {
    NavHost(
        navController = navController,
        startDestination = Home.route) {
        composable(Home.route) {
            HomeScreen(navController)
        }
        composable(TriviaInfo.route) {
            TriviaInfo(navController)
        }
        composable(Instructions.route) {
            InstructionScreen(navController)
        }
        composable(HighScores.route) {
            HighScoresScreen(navController)
        }
    }
}

package com.example.triviagame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.compose.TriviaGameTheme
import com.example.triviagame.navigation.Navigation


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TriviaGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   TriviaGame()
                }
            }
        }
    }
}

@Composable
fun TriviaGame() {
    val navController= rememberNavController()
    Navigation(navController)

}

@Preview(showBackground = true)
@Composable
fun TriviaGamePreview() {
    TriviaGameTheme {
        TriviaGame()
    }
}
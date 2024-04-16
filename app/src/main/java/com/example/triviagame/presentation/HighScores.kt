package com.example.triviagame.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.triviagame.R
import com.example.triviagame.navigation.Home
import com.example.triviagame.navigation.Instructions
import com.example.triviagame.navigation.TriviaInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HighScoresScreen(navController: NavHostController) {
    val viewModel: TriviaGameViewModel = viewModel()
    viewModel.retieveHighScores()
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(id = R.string.high_score_screen)) }) },

        content = { padding ->
           Column(verticalArrangement = Arrangement.SpaceAround,
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier.fillMaxSize()) {
               Spacer(modifier = Modifier.height(20.dp))
               Row(verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.Center,
                   modifier = Modifier.fillMaxWidth()

               ) {
                   Text(text = stringResource(id = R.string.my_high_score),
                       textAlign = TextAlign.Center,
                       fontWeight = FontWeight.ExtraBold
                   )
               }
               Spacer(modifier = Modifier.height(20.dp))
               Row() {
                   Column(verticalArrangement = Arrangement.Center,
                       horizontalAlignment = Alignment.CenterHorizontally,
                       modifier = Modifier
                           .fillMaxWidth()
                   ) {
                       MyHighScore(uiState)
                   }
               }
               Row(verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.Center,
                   modifier = Modifier.fillMaxWidth()

               ) {
                   Text(text = stringResource(id = R.string.all_time_high_score),
                       textAlign = TextAlign.Center,
                       fontWeight = FontWeight.ExtraBold
                   )
               }
               Spacer(modifier = Modifier.height(20.dp))

               Row() {
                   Column(verticalArrangement = Arrangement.Center,
                       horizontalAlignment = Alignment.CenterHorizontally,
                       modifier = Modifier
                           .fillMaxWidth()
                   ) {
                       AllTimeHighScores(uiState)}
               }

               
           }

        },
        bottomBar = {
            BottomAppBarLogo()
        }
    )

}

@Composable
fun AllTimeHighScores(
    uiState: TriviaUiState
) {
    val highScores = uiState.highScores
    LazyColumn {
        items(highScores) { highScore ->
            Text(
                text = "${highScore.ranking} - ${highScore.name} - ${highScore.highScore} points on ${highScore.date}",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun MyHighScore(
    uiState: TriviaUiState
) {
    Text(text = "My personal High Score: ${uiState.highScore} points",
        fontSize = 20.sp)
}

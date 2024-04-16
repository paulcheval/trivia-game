package com.example.triviagame.presentation


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.triviagame.R
import com.example.triviagame.navigation.HighScores
import com.example.triviagame.navigation.Instructions
import com.example.triviagame.navigation.TriviaInfo

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Awesome Trivia Experience") }) },

        content = { padding ->
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)) {
                Text(text = stringResource(id = R.string.welcome_message),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp)

                Button(
                    onClick = {
                      navController.navigate(TriviaInfo.route)
                    },
                    shape = RoundedCornerShape(10.dp),

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)

                ) {
                    Text(stringResource(id = R.string.play_game))
                }
                Button(
                    onClick = {
                        navController.navigate(Instructions.route)
                    },
                    shape = RoundedCornerShape(10.dp),

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)

                ) {
                    Text(stringResource(id = R.string.how_to_play))
                }

                Button(
                    onClick = {
                        navController.navigate(HighScores.route)
                    },
                    shape = RoundedCornerShape(10.dp),

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)

                ) {
                    Text(stringResource(id = R.string.see_high_scores))
                }
            }
        },
        bottomBar = {
            BottomAppBarLogo()
        }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
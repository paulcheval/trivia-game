package com.example.triviagame.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
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
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.triviagame.R
import com.example.triviagame.navigation.Home
import com.example.triviagame.network.HighScore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HighScoresScreen(navController: NavHostController) {
    val viewModel: TriviaGameViewModel = viewModel()
    viewModel.retieveHighScores()
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = { TriviaTopAppBar(" - High Scores") },

        content = { padding ->
           Column(verticalArrangement = Arrangement.SpaceEvenly,
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier.fillMaxSize()) {

               Spacer(modifier = Modifier.height(10.dp))

               Row(verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.Center,
                   modifier = Modifier.fillMaxWidth().weight(2f)) {

                       MyHighScore(uiState)

               }
               Row(verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.Center,
                   modifier = Modifier.fillMaxWidth().weight(0.5f)

               ) {
                   Text(text = stringResource(id = R.string.all_time_high_score),
                       style = MaterialTheme.typography.displayMedium
                   )
               }
               Spacer(modifier = Modifier.height(10.dp))

               Row(modifier = Modifier
                   .fillMaxWidth()
                   .weight(4f)) {
                   Column(verticalArrangement = Arrangement.Center,
                       horizontalAlignment = Alignment.CenterHorizontally,
                       modifier = Modifier
                           .fillMaxWidth()
                           .weight(1f)
                   ) {
                       AllTimeHighScores(uiState)}
               }
               Spacer(modifier = Modifier.height(10.dp))
               Row(modifier = Modifier
                   .fillMaxWidth()
                   .weight(1f)) {
                   Column(verticalArrangement = Arrangement.Bottom,
                       horizontalAlignment = Alignment.CenterHorizontally,
                       modifier = Modifier
                           .fillMaxWidth()
                   ) {
                       Button(
                           onClick = {
                               navController.navigate(Home.route)
                           },
                           shape = RoundedCornerShape(10.dp),

                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(5.dp)

                       ) {
                           Text(stringResource(id = R.string.go_back))
                       }
                   }
               }
           }

        },
        bottomBar = {
            BottomAppBarLogo()
        }
    )

}
@Composable
fun HighScoreItem(
   highScore: HighScore,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val color by animateColorAsState(targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
    else MaterialTheme.colorScheme.primaryContainer)
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessVeryLow
                    )
                )
                .background(color = color)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {

                HighScoreInformation(highScore)
                Spacer(modifier = Modifier.weight(1f))
                //HighScoreItemButton(expanded = expanded, onClick = { expanded = !expanded })
            }
//            if (expanded) {
//                HighScoreDetails(name = highScore.name, date = highScore.date,
//                    modifier = Modifier.padding(
//                        start = dimensionResource(id = R.dimen.padding_medium),
//                        top = dimensionResource(id = R.dimen.padding_small),
//                        end = dimensionResource(id = R.dimen.padding_medium),
//                        bottom = dimensionResource(id = R.dimen.padding_medium)
//                    ))
//            }
        }
    }
}
@Composable
private fun HighScoreItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier) {
        Icon(
            imageVector = if (expanded) Icons.Sharp.KeyboardArrowUp else Icons.Sharp.KeyboardArrowDown,
            contentDescription = stringResource(id = R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary)
    }

}
@Composable
fun HighScoreInformation(
    highScore: HighScore,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "# ${highScore.ranking.toString()} - ${highScore.highScore.toString()} points by ${highScore.name} on ${highScore.date}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )

    }
}

@Composable
fun HighScoreDetails(
    name: String,
    date: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = name,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = date,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
@Composable
fun AllTimeHighScores(
    uiState: TriviaUiState
) {
    val highScores = uiState.highScores
    LazyColumn {
        items(highScores) { highScore ->
            HighScoreItem(highScore = highScore)
            //Text(
            //    text = "${highScore.ranking} - ${highScore.name} - ${highScore.highScore} points on ${highScore.date}",
            //    fontSize = 20.sp
            //)
        }
    }
}

@Composable
fun MyHighScore(
    uiState: TriviaUiState
) {
    Text(text = "My personal High Score: ${uiState.highScore} points",
        style = MaterialTheme.typography.displayMedium)
}

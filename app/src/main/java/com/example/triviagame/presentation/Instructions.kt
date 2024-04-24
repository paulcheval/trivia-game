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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.triviagame.R
import com.example.triviagame.navigation.Home
import com.example.triviagame.navigation.Instructions
import com.example.triviagame.navigation.TriviaInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TriviaTopAppBar(message = " - How to Play") },

        content = { padding ->
           Column(verticalArrangement = Arrangement.SpaceAround,
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier.fillMaxSize()) {
               Spacer(modifier = Modifier.height(20.dp))
               Row(verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.Center,
                   modifier = Modifier.fillMaxWidth()

               ) {
                   Text(text = stringResource(id = R.string.rules_of_the_game),
                       textAlign = TextAlign.Center,
                       fontWeight = FontWeight.ExtraBold
                   )
               }
               Spacer(modifier = Modifier.height(20.dp))
               InstructionLine(instructionId = R.string.instruction_1)
               InstructionLine(instructionId = R.string.instruction_2)
               InstructionLine(instructionId = R.string.instruction_3)
               InstructionLine(instructionId = R.string.instruction_4)
               Spacer(modifier = Modifier.height(20.dp))

               Row() {
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
                       Button(
                           onClick = {
                               navController.navigate(TriviaInfo.route)
                           },
                           shape = RoundedCornerShape(10.dp),

                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(5.dp)

                       ) {
                           Text(stringResource(id = R.string.play_now))
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
fun InstructionLine(instructionId: Int) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)) {
        Text(text = stringResource(id = instructionId))
    }
}

@Preview
@Composable
fun InstructionsScreenPreview() {
    InstructionScreen(navController = rememberNavController())
}
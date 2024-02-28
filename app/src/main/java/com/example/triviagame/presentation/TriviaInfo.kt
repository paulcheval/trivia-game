package com.example.triviagame.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.triviagame.R
import com.example.triviagame.network.Question
import com.example.triviagame.network.TriviaNetworkItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriviaInfo() {
    val viewModel: TriviaGameViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    Log.d("TriviaInfo", uiState.toString())

    var showEndOfGameDialog by remember {
        mutableStateOf(false)
    }

    if (showEndOfGameDialog) {
        Alert(title = stringResource(id = R.string.game_over),
            textLine1 = stringResource(id = R.string.play_again),
            onDismiss = {  showEndOfGameDialog = false },
            onConfirm = {
                viewModel.reloadData()
                showEndOfGameDialog = false
            })
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Awesome Trivia Experience") }) },

        content = { padding ->
            Log.d("TriviaInfo-top", "Number of questions - $uiState.questions.size")
            Log.d("TriviaInfo-top", "Current question - ${uiState.currentQuestion}")
            if (uiState.questions.isEmpty()) {
                Text(text = "No trivia today")
            } else if (uiState.currentQuestion == -1) {
                showEndOfGameDialog = true
                Log.d("TriviaInfo-top", "Processing -1 question")
                Text(text = "Game Over")
                Toast.makeText(LocalContext.current, "Game Over", Toast.LENGTH_LONG).show()
            } else {
                val currentQuestion = uiState.questions[uiState.currentQuestion]
                TriviaScreen(
                    viewModel = viewModel,
                    currentQuestion = currentQuestion,
                    modifier = Modifier.padding(padding))
                /*Column(modifier = Modifier.fillMaxSize()) {
                    QuestionCard(
                        viewModel= viewModel,
                        question = currentQuestion,
                        allAnswers = viewModel.getListOfAnswers(currentQuestion.correctAnswer, currentQuestion.incorrectAnswers),
                        modifier = Modifier.padding(padding)
                    )
                }*/
            }
        },
        bottomBar = { BottomAppBar { Text("You Are Here") } }
    )

}

@Composable
fun TriviaScreen(
    viewModel: TriviaGameViewModel,
    currentQuestion: TriviaNetworkItem,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround) {
        QuestionCard(
            viewModel= viewModel,
            question = currentQuestion,
            allAnswers = viewModel.getListOfAnswers(currentQuestion.correctAnswer, currentQuestion.incorrectAnswers),
            modifier = modifier
        )
    }
}

@Composable
fun QuestionCard(
    question: TriviaNetworkItem,
    allAnswers: List<String>,
    viewModel: TriviaGameViewModel,
    modifier: Modifier = Modifier) {
    Log.d("TriviaInfo - QuestionCard", question.toString())

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    var selectedOption by remember {
        mutableStateOf("")
    }


        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
            verticalArrangement = Arrangement.Center) {
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        text = stringResource(id = R.string.category_label),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        fontStyle = FontStyle.Normal,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                    Text(
                        text = stringResource(id = Categories.valueOf(question.category).presentationName),
                        textAlign = TextAlign.Left,
                        fontStyle = FontStyle.Normal,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
                Column {
                    Text(
                        text = stringResource(id = R.string.difficulty_label),
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                    Text(
                        text = stringResource(id = Difficulty.valueOf(question.difficulty).presentationName),
                        textAlign = TextAlign.Left,
                        fontStyle = FontStyle.Normal,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
                Column {
                    Text(
                        text = stringResource(id = R.string.question_value_label),
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                    Text(
                        text = viewModel.questionValue(question).toString(),
                        textAlign = TextAlign.Left,
                        fontStyle = FontStyle.Normal,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }



            }


            Divider(thickness = 3.dp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = question.question.text)
            Divider(thickness = 1.dp)
            allAnswers.forEach {answer ->
                Row (verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = (answer == selectedOption),
                        onClick = { selectedOption = answer }
                    )
                    Text(text = answer)
                }
            }

            Text(text = question.correctAnswer)

            val incorrectMessage = stringResource(id = R.string.incorrect_answer_message)
            val correctMessage = stringResource(id = R.string.correct_answer_message)

            Button(
                onClick = {
                    if (viewModel.isAnswerCorrect(
                            selectedOption,
                            correctAnswer = question.correctAnswer
                        )
                    ) {
                        Toast.makeText(context, correctMessage, Toast.LENGTH_SHORT).show()
                        viewModel.incrementScore(question)
                        viewModel.resetAttempts()
                        viewModel.getNextQuestion()
                    } else {

                        Toast.makeText(context, incorrectMessage, Toast.LENGTH_SHORT).show()
                        viewModel.incrementAttempts()
                    }
                },
                shape = RoundedCornerShape(10.dp),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)

            ) {
                Text(text = "Validate answer")
            }




            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()) {

                Image(
                    painter = painterResource(id = Categories.valueOf(question.category).image),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(330.dp, 200.dp)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp)))

                Button(onClick = { viewModel.getNextQuestion() }) {
                    Text(text = "Next")
                }
                Divider()
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = stringResource(id = R.string.current_score_label),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.padding(start = 10.dp)
                    )

                    Text(text = uiState.currentScore.toString(),
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(start = 2.dp))
                }
            }


        }

    }



@Composable
fun Answers(incorrectAnswer: String) {
    Text(text = incorrectAnswer)
}

@Preview
@Composable
fun TriviaScreenPreview(){
    val currentQuestion = TriviaNetworkItem(
        category = "History",
        correctAnswer = "A",
        question = Question("Blah blahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblah?"),
        difficulty = "Hard",
        incorrectAnswers = listOf("B","C","D"),
        id = "1",
        isNiche = false,
        regions = emptyList(),
        tags = emptyList(),
        type = "type"
    )
    TriviaScreen(viewModel = TriviaGameViewModel(),
        currentQuestion
    )
}

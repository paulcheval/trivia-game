package com.example.triviagame.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.triviagame.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriviaTopAppBar(message: String, modifier: Modifier = Modifier) {
   // TopAppBar(title = { Text("Awesome Trivia Experience" + message) })
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    painter = painterResource(id = R.drawable.brain),
                    contentDescription = null )
                Text(text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayLarge)
            }
        },
        modifier = modifier)
}
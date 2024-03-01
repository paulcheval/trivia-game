package com.example.triviagame.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.triviagame.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomAppBarLogo() {
    BottomAppBar(
        modifier = Modifier.height(30.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            stringResource(id = R.string.bottom_Message),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .width(150.dp)
                .weight(1f)
                .basicMarquee()
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
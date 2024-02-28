package com.example.triviagame.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.triviagame.R

@Composable
fun Alert(
    textLine1: String = "",
    textLine2: String = "",
    title: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text =title)
        },
        text = {
            Column {
                Text(text = textLine1)
                Text(text = textLine2)
            }

        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(id = R.string.replay_game_label))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.stop_play_label))
            }
        },


    )

}

package com.example.triviagame.presentation

import androidx.annotation.StringRes
import com.example.triviagame.R

enum class Difficulty(
    @StringRes val presentationName: Int,
    val scoring: List<Int>,

) {
    easy(R.string.easy, listOf(10, 7, 4, 1 )),
    medium(R.string.medium, listOf(20, 14, 8, 1)),
    hard(R.string.hard, listOf(30, 21, 12, 1))
}
package com.example.triviagame.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.triviagame.R

enum class Categories(
    @StringRes val  presentationName: Int,
    @DrawableRes val  image: Int) {
    arts_and_literature(R.string.arts_and_literature, R.drawable.art_and_literature),
    film_and_tv(R.string.film_and_tv, R.drawable.film_and_tv),
    food_and_drink(R.string.food_and_drink, R.drawable.food_and_drink),
    general_knowledge(R.string.general_knowledge, R.drawable.general_knowledge),
    geography(R.string.geography, R.drawable.geography),
    history(R.string.history, R.drawable.history),
    music(R.string.music, R.drawable.music),
    science(R.string.science, R.drawable.science),
    society_and_culture(R.string.society_and_culture, R.drawable.society_and_culture),
    sport_and_leisure(R.string.sport_and_leisure, R.drawable.sport_and_leisure),
}
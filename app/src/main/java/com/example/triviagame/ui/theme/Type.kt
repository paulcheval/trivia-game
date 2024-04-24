package com.example.triviagame.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.sp
import com.example.triviagame.R

// Set of Material typography styles to start with
val AbrilFatFace = FontFamily(Font(R.font.abrilfatface_regular))
val Montserrat = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)
// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = AbrilFatFace,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),


    headlineMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    headlineLarge =  TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),




)
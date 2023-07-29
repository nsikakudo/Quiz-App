package com.project.quizapp.utils

import com.project.quizapp.data.Questions

object Constants {
    const val MAX_NO_OF_QUESTIONS = 3
    const val SCORE_INCREASE = 10
    const val USER_NAME : String = "user_name"
}

val questionList: MutableList<Questions> = mutableListOf(
    Questions(
        question = "What is the smallest planet in our solar system?",
        options = listOf("Mars", "Mercury", "Pluto", "Sun"),
        correctAnswer = "Mercury"
    ),
    Questions(
        question = "What are the five colours of the Olympic rings??",
        options = listOf("Blue, Black, Yellow, Green and Red", "Black, Yellow, Pink, Green and Blue", "Red, Blue, White, Green and Yellow", "Blue, Yellow, Black, Green and Red"),
        correctAnswer = "Blue, Yellow, Black, Green and Red"
    ),
    Questions(
        question = "Which famous scientist was known as the “father of the computer”?",
        options = listOf("Steve Jobs", "Elon Musk", "Thomas Edison", "Charles Babbage"),
        correctAnswer = "Charles Babbage"
    )
)
package com.example.quizgame.data

data class Question(
    val questions: String,
    val choices: List<String>,
    val correctAnswer: String
)
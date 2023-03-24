package com.example.quizgame.data

data class QuestionData(
    val question: String,
    val choices: List<String>,
    val correctAnswer: String
)
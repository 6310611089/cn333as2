package com.example.quizgame.ui

data class GameUiState(
    val currentQuestion: String = "",
    val currentQuestionCount: Int = 1,
    val score: Int = 0,
    val isFinished: Boolean = false
)
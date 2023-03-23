package com.example.quizgame.ui

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.quizgame.data.QuestionData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val questions = listOf(
        QuestionData(
            "In which city was Martin Luther King Jr. assassinated?",
            listOf("New York", "Austin", "Miami", "Memphis"),
            "Memphis"),
        QuestionData(
            "Which country won the first Football World Cup in 1930?",
            listOf("Brazil", "Portugal", "Italy", "Uruguay"),
            "Uruguay"),
        QuestionData(
            "Which country was not part of the Axis Powers during WWII?",
            listOf("Germany", "Soviet Union", "Italy", "Japan"),
            "Soviet Union"),
        QuestionData(
            "What does NASA stand for?",
            listOf("National Aeronautics and Space Administration", "Nautical And Space Association", "National Aeronautics and Space Association", "New Aeronautics and Spacial Administration"),
            "National Aeronautics and Space Administration"),
        QuestionData(
            "Who was the first woman to win a Nobel Prize?",
            listOf("Mother Teresa", "Marie Curie", "Jane Adams", "Alva Myrdal"),
            "Marie Curie"),
        QuestionData(
            "What is the name of the gem in the movie Titanic?",
            listOf("Call of the Ocean", "Heart of Love", "Heart of the Ocean", "Call of Love"),
            "Heart of the Ocean"),
        QuestionData(
            "How many bones are there in an adult human body?",
            listOf("186", "206", "286", "306"),
            "206"),
        QuestionData(
            "Which Nobel Prize did Winston Churchill win?",
            listOf("Literature", "Peace", "Chemistry", "Physics"),
            "Literature"),
        QuestionData(
            "Which one of the following is not a character in the cartoon “The Powerpuff Girls”?",
            listOf("Blossom", "Butterfly", "Bubbles", "Buttercup"),
            "Butterfly"),
        QuestionData(
            "What’s Garfield favourite food?",
            listOf("Pizza", "Lasagna", "Burger", "Sandwich"),
            "Lasagna"),
        )

    fun checkAnswer(answer: String) {
        var currentQuestionIndex = _uiState.value.currentQuestionIndex
        if (answer == questions[currentQuestionIndex].correctAnswer) {
            val score = _uiState.value.score + 1
            _uiState.update { currentState ->
                currentState.copy(score = score)
            }
        }
    }

    fun countQuestion() {
            _uiState.update { currentState ->
                currentState.copy(currentQuestionCount = _uiState.value.currentQuestionCount + 1) }
    }

    fun nextQuestion(): QuestionData? {
        var ansQuestionList =  _uiState.value.ansQuestionList
        var currentQuestion = questions[_uiState.value.currentQuestionIndex]
        if (ansQuestionList.size > 0) {
            var currentQuestionIndex = ansQuestionList.randomOrNull()?:_uiState.value.currentQuestionIndex
            currentQuestion = questions[currentQuestionIndex]
            ansQuestionList.remove(currentQuestionIndex)
            _uiState.update { currentState ->
                currentState.copy(
                    currentQuestionIndex = currentQuestionIndex,
                    ansQuestionList = ansQuestionList,
                ) }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isFinished = true,
                ) }
        }
        return currentQuestion
    }

    fun resetGame() {
        _uiState.update { currentState ->
            currentState.copy(
                score = 0,
                ansQuestionList = arrayListOf<Int>(0,1,2,3,4,5,6,7,8,9),
                isFinished = false,
                currentQuestionCount = 1,
            ) }
    }
}
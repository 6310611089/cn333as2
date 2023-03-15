package com.example.quizgame.ui

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizgame.R
import com.example.quizgame.ui.theme.QuizGameTheme


@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameviewModel: GameViewModel = viewModel()
) {
    val gameUiState by gameviewModel.uiState.collectAsState()

    var currentQuestion by remember { mutableStateOf(gameviewModel.nextQuestion()) }
    var choiceChecked by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GameStatus()
        GameLayout()
        if (gameUiState.isFinished) {
            FinalScoreDialog(score = gameUiState.score,
                onPlayAgain = {
                    gameviewModel.resetGame()
                    currentQuestion = gameviewModel.nextQuestion()
                    choiceChecked = ""
                }
            )
        }
    }
}

@Composable
fun GameStatus(
    modifier: Modifier = Modifier,
    gameviewModel: GameViewModel = viewModel()
) {
    val gameUiState by gameviewModel.uiState.collectAsState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(48.dp),
    ) {
        Text(
            text = stringResource(R.string.question_count, gameUiState.currentQuestionCount),
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = stringResource(R.string.score, gameUiState.score),
            fontSize = 18.sp
        )
    }
}

@Composable
fun GameLayout(
    modifier: Modifier = Modifier,
    gameviewModel: GameViewModel = viewModel()
) {

    var currentQuestion by remember { mutableStateOf(gameviewModel.nextQuestion()) }
    var choiceClicked by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = currentQuestion!!.question,
            fontSize = 45.sp,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
    }

    currentQuestion!!.choices.forEach { choice ->
        Row(
        ) {
            TextButton(onClick = {
                currentQuestion = gameviewModel.nextQuestion()
                choiceClicked = ""
            }
            ) {
                Text(text = choice)
            }
        }
    }
}

@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(R.string.congratulations)) },
        text = { Text(stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onPlayAgain()
                }
            ) {
                Text(text = stringResource(R.string.play_again))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    QuizGameTheme {
        GameScreen()
    }
}
package com.ranking.trivia.latam.presentation.screens.play

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.domain.models.Question
import com.ranking.trivia.latam.domain.models.QuestionLevel
import com.ranking.trivia.latam.presentation.ui.dialogs.CorrectDialog
import com.ranking.trivia.latam.presentation.ui.dialogs.IncorrectDialog
import com.ranking.trivia.latam.presentation.ui.dialogs.TimeUpDialog
import com.ranking.trivia.latam.presentation.utils.LongPressDraggable
import com.ranking.trivia.latam.presentation.utils.playSound

@Composable
fun PlayScreen(
    viewModel: PlayViewModel = hiltViewModel(),
    onResetScreen: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val question by viewModel.question.collectAsState()
    val flags by viewModel.flags.collectAsState()
    val spaces by viewModel.spaces.collectAsState()

    var timeUp by remember { mutableStateOf(false) }
    var showTimeUpDialog by remember { mutableStateOf(false) }
    var showIncorrectDialog by remember { mutableStateOf(false) }
    var showCorrectDialog by remember { mutableStateOf(false) }

    TimeUpDialog(
        isVisible = showTimeUpDialog,
        onRetryClicked = {
            // todo: ads every 4 errors
            showTimeUpDialog = false
            onResetScreen()
        },
        onExitClicked = {
            showTimeUpDialog = false
            onBack()
        }
    )
    IncorrectDialog(
        isVisible = showIncorrectDialog,
        onRetryClicked = {
            // todo: ads every 4 errors
            showIncorrectDialog = false
            onResetScreen()
        },
        onExitClicked = {
            showIncorrectDialog = false
            onBack()
        }
    )
    CorrectDialog(
        isVisible = showCorrectDialog,
        onNextClicked = {
            showCorrectDialog = false
            viewModel.saveQuestionAlreadyPlayed(question)
            Handler(Looper.getMainLooper()).postDelayed({
                onResetScreen()
            }, 500L)
        },
        onExitClicked = {
            showCorrectDialog = false
            viewModel.saveQuestionAlreadyPlayed(question)
            Handler(Looper.getMainLooper()).postDelayed({
                onBack()
            }, 500L)
        }
    )

    LaunchedEffect(Unit) {
        viewModel.getQuestionToPlay()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        MainBackground()
        LongPressDraggable(modifier = Modifier.fillMaxSize()) {

            question?.let {
                PlayScreenHeader(
                    level = it.level,
                    question = it.description,
                    onBack = { onBack() }
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 160.dp, bottom = 130.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(0.6f)
                        .padding(top = 10.dp)
                ) {
                    itemsIndexed(items = spaces) { i, space ->
                        CardEmptySpace(
                            index = i,
                            emptySpace = space,
                            viewModel = viewModel
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.weight(0.4f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(vertical = 10.dp)
                ) {
                    items(flags) { flag ->
                        CardFlag(flag, !flag.alreadyPlayed)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .align(Alignment.BottomCenter)
            ) {
                BottomButton(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxHeight(),
                    timeUp = timeUp,
                    emptySpaces = spaces.filter { it.flag == null }
                ) {
                    val responseIsCorrect = viewModel.verifyIfListIsCorrect(spaces.map { it.flag?.id!! }, question!!)
                    if (responseIsCorrect && viewModel.shouldPlaySound()) {
                        playSound(context, R.raw.sound_success)
                        showCorrectDialog = true
                    } else {
                        if (viewModel.shouldPlaySound()) {
                            playSound(context, R.raw.sound_error)
                        }
                        showIncorrectDialog = true
                    }
                }

                question?.let {
                    CountdownTimer(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight(),
                        totalTime = getTimeAccordingLevel(it.level),
                        isPaused = (showCorrectDialog || showIncorrectDialog)
                    ) {
                        timeUp = true
                        showTimeUpDialog = true
                        if (viewModel.shouldPlaySound()) {
                            playSound(context, R.raw.sound_error)
                        }
                    }
                }
            }
        }
    }
}

fun getTimeAccordingLevel(level: QuestionLevel): Long {
    return when (level) {
        QuestionLevel.I,
        QuestionLevel.II,
        QuestionLevel.III,
        QuestionLevel.IV -> 60000L
        QuestionLevel.V,
        QuestionLevel.VI,
        QuestionLevel.VII -> 50000L
        QuestionLevel.VIII,
        QuestionLevel.IX,
        QuestionLevel.X -> 45000L
        QuestionLevel.XI -> 40000L
        QuestionLevel.XII -> 30000L
        QuestionLevel.XIII -> 20000L
    }
}

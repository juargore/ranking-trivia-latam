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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.presentation.ui.dialogs.CorrectDialog
import com.ranking.trivia.latam.presentation.ui.dialogs.IncorrectDialog
import com.ranking.trivia.latam.presentation.ui.dialogs.TimeUpDialog
import com.ranking.trivia.latam.presentation.utils.LongPressDraggable
import com.ranking.trivia.latam.presentation.utils.loadAndShowAd
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

    val adUnitId = "ca-app-pub-3940256099942544/1033173712" // todo: replace the real one

    TimeUpDialog(
        isVisible = showTimeUpDialog,
        onExitClicked = { showTimeUpDialog = false; onBack() },
        onRetryClicked = {
            if (viewModel.shouldDisplayAd()) {
                loadAndShowAd(context, adUnitId,
                    onAdDismissed = {
                        viewModel.resetErrors()
                        showTimeUpDialog = false
                        onResetScreen()
                    })
            } else {
                showTimeUpDialog = false
                onResetScreen()
            }
        }
    )

    IncorrectDialog(
        isVisible = showIncorrectDialog,
        onExitClicked = { showIncorrectDialog = false; onBack() },
        onRetryClicked = {
            if (viewModel.shouldDisplayAd()) {
                loadAndShowAd(context, adUnitId,
                    onAdDismissed = {
                        viewModel.resetErrors()
                        showIncorrectDialog = false
                        onResetScreen()
                    })
            } else {
                showIncorrectDialog = false
                onResetScreen()
            }
        }
    )

    CorrectDialog(
        isVisible = showCorrectDialog,
        onNextOrExitClicked = { isExit ->
            showCorrectDialog = false
            viewModel.saveQuestionAlreadyPlayed(question)
            Handler(Looper.getMainLooper()).postDelayed({
                if (isExit) onBack() else onResetScreen()
            },500L)
        }
    )

    LaunchedEffect(Unit) {
        viewModel.getQuestionToPlay()
        if (viewModel.shouldDisplayAdAtStart()) {
            loadAndShowAd(context, adUnitId,
                onAdFailedToLoad = { onBack() },
                onAdDismissed = { viewModel.resetErrors() }
            )
        }
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
                    .padding(top = 160.dp, bottom = 170.dp)
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

            // todo: add bottom ad banner here
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
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
                    if (responseIsCorrect) {
                        if (viewModel.shouldPlaySound()) playSound(context, R.raw.sound_success)
                        showCorrectDialog = true
                    } else {
                        if (viewModel.shouldPlaySound()) playSound(context, R.raw.sound_error)
                        viewModel.incrementCounterOfErrors()
                        showIncorrectDialog = true
                    }
                }

                question?.let {
                    CountdownTimer(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight(),
                        totalTime = viewModel.getTimeAccordingLevel(it.level),
                        isPaused = (showCorrectDialog || showIncorrectDialog)
                    ) {
                        timeUp = true
                        showTimeUpDialog = true

                        if (viewModel.shouldPlaySound()) playSound(context, R.raw.sound_error)
                        viewModel.incrementCounterOfErrors()
                    }
                }
            }
        }
    }
}

package com.ranking.trivia.latam.presentation.screens.play

import android.os.Handler
import android.os.Looper
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.domain.models.Question
import com.ranking.trivia.latam.presentation.screens.hall.HallOfFameViewModel
import com.ranking.trivia.latam.presentation.screens.home.AdmobBanner
import com.ranking.trivia.latam.presentation.theme.CustomBlue
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedBold
import com.ranking.trivia.latam.presentation.theme.strongShadow
import com.ranking.trivia.latam.presentation.ui.dialogs.CorrectDialog
import com.ranking.trivia.latam.presentation.ui.dialogs.IncorrectDialog
import com.ranking.trivia.latam.presentation.ui.dialogs.SaveRankingDialog
import com.ranking.trivia.latam.presentation.ui.dialogs.ScoreUI
import com.ranking.trivia.latam.presentation.ui.dialogs.TimeUpDialog
import com.ranking.trivia.latam.presentation.utils.LongPressDraggable
import com.ranking.trivia.latam.presentation.utils.PLAY_BOTTOM_SMALL_BANNER_ID
import com.ranking.trivia.latam.presentation.utils.PLAY_FULL_SCREEN_BANNER_ID
import com.ranking.trivia.latam.presentation.utils.loadAndShowAd
import com.ranking.trivia.latam.presentation.utils.playSound
import com.ranking.trivia.latam.presentation.utils.resetApplication

@Composable
fun PlayScreen(
    viewModel: PlayViewModel = hiltViewModel(),
    vmh: HallOfFameViewModel = hiltViewModel(),
    onResetScreen: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val gameCompleted by viewModel.gameCompleted.collectAsState()
    val question by viewModel.question.collectAsState()
    val flags by viewModel.flags.collectAsState()
    val spaces by viewModel.spaces.collectAsState()

    var timeUp by remember { mutableStateOf(false) }
    var showTimeUpDialog by remember { mutableStateOf(false) }
    var showIncorrectDialog by remember { mutableStateOf(false) }
    var showCorrectDialog by remember { mutableStateOf(false) }
    var animateScore: Triple<Boolean, Boolean, Question>? by remember { mutableStateOf(null) }

    SaveRankingDialog(gameCompleted, vmh) {
        context.resetApplication()
    }

    TimeUpDialog(
        isVisible = showTimeUpDialog,
        onExitClicked = { showTimeUpDialog = false; onBack() },
        onRetryClicked = {
            if (viewModel.shouldDisplayAd()) {
                loadAndShowAd(context, PLAY_FULL_SCREEN_BANNER_ID,
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
                loadAndShowAd(context, PLAY_FULL_SCREEN_BANNER_ID,
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
            loadAndShowAd(context, PLAY_FULL_SCREEN_BANNER_ID,
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
                    .padding(top = 160.dp, bottom = 180.dp)
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier.align(Alignment.TopCenter),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomButton(
                        modifier = Modifier
                            .weight(.4f)
                            .fillMaxHeight(),
                        timeUp = timeUp,
                        emptySpaces = spaces.filter { it.flag == null }
                    ) {
                        val responseIsCorrect = viewModel.verifyIfListIsCorrect(spaces.map { it.flag?.id!! }, question!!)
                        if (responseIsCorrect) {
                            if (viewModel.shouldPlaySound()) playSound(context, R.raw.sound_success)
                            animateScore = Triple(true, true, question!!)
                            vmh.incrementScore(question!!, true)
                            showCorrectDialog = true
                        } else {
                            if (viewModel.shouldPlaySound()) playSound(context, R.raw.sound_error)
                            viewModel.incrementCounterOfErrors()
                            animateScore = Triple(true, false, question!!)
                            vmh.incrementScore(question!!, false)
                            showIncorrectDialog = true
                        }
                    }

                    question?.let {
                        CountdownTimer(
                            modifier = Modifier
                                .weight(.4f)
                                .fillMaxHeight(),
                            totalTime = viewModel.getTimeAccordingLevel(it.level),
                            isPaused = (showCorrectDialog || showIncorrectDialog)
                        ) {
                            timeUp = true
                            showTimeUpDialog = true
                            if (viewModel.shouldPlaySound()) playSound(context, R.raw.sound_error)
                            animateScore = Triple(true, false, it)
                            vmh.incrementScore(it, false)
                            viewModel.incrementCounterOfErrors()
                        }
                    }

                    Box(
                        modifier = Modifier.weight(.2f),
                        contentAlignment = Alignment.Center
                    ) {
                        ScoreUI(score = vmh.getTotalScore())
                        animateScore?.let { sc ->
                            AnimateScoreNumber(sc.first, vmh.getPointsToAnimate(sc.third, sc.second))
                        }
                    }
                }

                AdmobBanner(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    adId = PLAY_BOTTOM_SMALL_BANNER_ID
                )
            }
        }
    }
}

@Composable
fun AnimateScoreNumber(visible: Boolean, score: Int, modifier: Modifier = Modifier) {
    if (visible) {
        val offsetY = remember { Animatable(0f) }
        val alpha = remember { Animatable(1f) }

        LaunchedEffect(Unit) {
            offsetY.animateTo(
                targetValue = -150f, // sube
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
            )
            alpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
        }

        Box(
            modifier = modifier
                .offset { IntOffset(0, offsetY.value.toInt()) }
                .alpha(alpha.value),
            contentAlignment = Alignment.Center
        ) {
            val scoreString = if (score > 0) "+$score" else score.toString()
            val scoreColor = if (score > 0) Color.White else Color.Red
            Text(
                text = scoreString,
                fontSize = 30.sp,
                color = scoreColor,
                fontFamily = fredokaCondensedBold,
                style = TextStyle(shadow = strongShadow),
                textAlign = TextAlign.Center
            )
        }
    }
}

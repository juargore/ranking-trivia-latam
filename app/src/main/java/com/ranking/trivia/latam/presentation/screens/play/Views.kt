package com.ranking.trivia.latam.presentation.screens.play

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.domain.models.EmptySpace
import com.ranking.trivia.latam.domain.models.QuestionLevel
import com.ranking.trivia.latam.presentation.theme.CustomBlue
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedBold
import com.ranking.trivia.latam.presentation.theme.regularShadow
import com.ranking.trivia.latam.presentation.utils.VignetteInverseEffect

@Composable
fun MainBackground() {
    Image(
        painter = painterResource(id = R.drawable.pyramid_one),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    VignetteInverseEffect(Modifier.fillMaxSize())
}


@Composable
fun PlayScreenHeader(
    level: QuestionLevel,
    question: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(160.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .height(150.dp)
                .background(CustomBlue.copy(alpha = 0.4f))
        ) {
            HeaderBackAndCategory(onBack = onBack)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = question,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = fredokaCondensedBold,
                    color = Color.White,
                    lineHeight = 24.sp,
                    style = TextStyle(shadow = regularShadow),
                    modifier = Modifier.wrapContentSize()
                )
            }

            HeaderDivisions(modifier = Modifier)
        }

        HeaderLevel(
            modifier = Modifier.align(Alignment.BottomCenter),
            level = level.ordinal + 1
        )
    }

}

@Composable
fun HeaderDivisions(
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .background(Color.Gray)
            .shadow(5.dp)
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(Color.LightGray)
            .shadow(5.dp)
        )
    }
}


@Composable
fun HeaderBackAndCategory(
    onBack: () -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(Color.White)
                .align(Alignment.CenterStart)
                .border(1.dp, Color.Black, CircleShape)
                .clickable { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Image(
            painter = painterResource(id = R.drawable.logo_no_background_letters),
            modifier = Modifier
                .height(35.dp)
                .width(40.dp)
                .align(Alignment.CenterEnd),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
        )
    }
}

@Composable
fun HeaderLevel(
    modifier: Modifier = Modifier,
    level: Int
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .background(Color.Yellow, shape = RoundedCornerShape(20.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(20.dp))
    ) {
        Text(
            text = "Nivel $level",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontFamily = fredokaCondensedBold,
            color = Color.DarkGray,
            modifier = Modifier.padding(
                vertical = 4.dp,
                horizontal = 16.dp
            )
        )
    }
}

@Composable
fun CountdownTimer(
    modifier: Modifier = Modifier,
    totalTime: Long,
    isPaused: Boolean,
    onTimeFinish: () -> Unit
) {
    var timeLeft by remember { mutableLongStateOf(totalTime) }
    var isTimerRunning by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = isPaused, key2 = timeLeft) {
        if (isTimerRunning && !isPaused) {
            if (timeLeft > 0) {
                kotlinx.coroutines.delay(1000L)
                timeLeft -= 1000L
            } else {
                isTimerRunning = false
                onTimeFinish()
            }
        }
    }

    TimerView(modifier, timeLeft)
}

@SuppressLint("DefaultLocale")
@Composable
fun TimerView(
    modifier: Modifier,
    timeLeft: Long
) {
    val limitTime = 7000L
    val minutes = (timeLeft / 1000) / 60
    val seconds = (timeLeft / 1000) % 60

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Box(
            modifier = modifier
                .wrapContentHeight()
                .background(Color.Yellow.copy(alpha = 0.5f), shape = RoundedCornerShape(12.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                fontSize = 34.sp,
                textAlign = TextAlign.Center,
                fontFamily = fredokaCondensedBold,
                color = if (timeLeft < limitTime) Color.Red else Color.DarkGray,
                style = TextStyle(shadow = regularShadow),
                modifier = Modifier.padding(15.dp)
            )
        }
    }
}

@Composable
fun BottomButton(
    modifier: Modifier = Modifier,
    timeUp: Boolean,
    emptySpaces: List<EmptySpace>,
    onClick: () -> Unit
) {

    var buttonEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(timeUp, emptySpaces) {
        buttonEnabled = (!timeUp && emptySpaces.isEmpty())
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Box(
            modifier = modifier
                .wrapContentHeight()
                .background(
                    color = if (buttonEnabled) CustomBlue else Color.LightGray.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 2.dp,
                    color = if (buttonEnabled) Color.Black else Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable {
                    if (buttonEnabled) {
                        onClick()
                    }
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Listo!!",
                fontSize = 34.sp,
                textAlign = TextAlign.Center,
                fontFamily = fredokaCondensedBold,
                color = if (buttonEnabled) Color.White else Color.White.copy(alpha = 0.5f),
                style = if (buttonEnabled) TextStyle(shadow = regularShadow) else LocalTextStyle.current,
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 20.dp)
            )
        }
    }
}

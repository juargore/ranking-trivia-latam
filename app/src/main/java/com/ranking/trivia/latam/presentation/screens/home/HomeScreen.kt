package com.ranking.trivia.latam.presentation.screens.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.presentation.theme.CustomBlue
import com.ranking.trivia.latam.presentation.theme.Orange
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedMedium
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedSemiBold
import com.ranking.trivia.latam.presentation.theme.regularShadow
import com.ranking.trivia.latam.presentation.ui.dialogs.AboutDialog
import com.ranking.trivia.latam.presentation.ui.dialogs.OptionsDialog
import com.ranking.trivia.latam.presentation.ui.dialogs.TutorialDialog
import com.ranking.trivia.latam.presentation.utils.Pulsating
import com.ranking.trivia.latam.presentation.utils.VignetteInverseEffect
import com.ranking.trivia.latam.presentation.utils.openUrl
import com.ranking.trivia.latam.presentation.utils.playSound

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToPlayScreen: () -> Unit
) {
    val context = LocalContext.current

    var showNewerVersionButton by remember { mutableStateOf(false) }
    var showOptionsDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showTutorialDialog by remember { mutableStateOf(false) }

    viewModel.gameHasNewerVersion(
        (context.packageManager.getPackageInfo(context.packageName, 0).versionCode)
    ) { gameHasTheNewestVersion ->
        if (!gameHasTheNewestVersion) showNewerVersionButton = true
    }

    OptionsDialog(
        isVisible = showOptionsDialog,
        isSoundEnabled = viewModel.shouldPlaySound(),
        onSaveClicked = { enableSound: Boolean ->
            showOptionsDialog = false
            viewModel.saveEnableSound(enableSound)
        },
        onExitClicked = { showOptionsDialog = false }
    )
    AboutDialog(
        isVisible = showAboutDialog,
        onExitClicked = { showAboutDialog = false }
    )
    TutorialDialog(
        isVisible = showTutorialDialog,
        onExitClicked = { showTutorialDialog = false }
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.pyramid_main_screen),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        VignetteInverseEffect(Modifier.fillMaxSize())

        Text(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            text = "v${(context.packageManager.getPackageInfo(context.packageName, 0).versionName)}",
            color = Color.White,
            fontFamily = fredokaCondensedSemiBold,
            fontSize = 18.sp,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.weight(0.5f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_no_background_letters),
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
                Column(
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    if (showNewerVersionButton) {
                        CircledButtonStart(buttonType = HomeButtonType.NewVersion) {
                            // todo: real URL from google play
                            openUrl(context, "https://play.google.com/store/games")
                        }
                    }
                    CircledButtonStart(buttonType = HomeButtonType.Settings) {
                        showOptionsDialog = true
                    }
                }
            }

            Column(
                modifier = Modifier.weight(0.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ButtonStart(
                    context = context,
                    buttonType = HomeButtonType.Start,
                    onClick = { onNavigateToPlayScreen() },
                    content = {
                        Text(
                            text = "Iniciar",
                            fontSize = 34.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = fredokaCondensedSemiBold,
                            color = Color.DarkGray,
                            style = TextStyle(shadow = regularShadow),
                            modifier = Modifier.padding(vertical = 15.dp, horizontal = 60.dp)
                        )
                    }
                )

                ButtonStart(
                    context = context,
                    buttonType = HomeButtonType.Tutorial,
                    onClick = { showTutorialDialog = true },
                    content = {
                        Text(
                            text = "Cómo jugar?",
                            fontSize = 26.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = fredokaCondensedSemiBold,
                            color = Color.DarkGray,
                            style = TextStyle(shadow = regularShadow),
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 30.dp)
                        )
                    }
                )

                ButtonStart(
                    context = context,
                    buttonType = HomeButtonType.About,
                    onClick = { showAboutDialog = true },
                    content = {
                        Text(
                            text = "Acerca de",
                            fontSize = 26.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = fredokaCondensedSemiBold,
                            color = Color.DarkGray,
                            style = TextStyle(shadow = regularShadow),
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 30.dp)
                        )
                    }
                )

            }

        }
    }
}

@Composable
fun ButtonStart(
    viewModel: HomeViewModel = hiltViewModel(),
    context: Context,
    buttonType: HomeButtonType,
    playSound: Boolean = true,
    onClick: () -> Unit,
    content: @Composable (() -> Unit)
) {
    val boxContent = @Composable {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .background(
                    color = Color.Yellow.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(24.dp)
                )
                .border(
                    width = 3.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(24.dp)
                )
                .clickable {
                    onClick()
                    if (playSound && viewModel.shouldPlaySound()) {
                        playSound(context, R.raw.sound_next_level)
                    }
                },
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }

    if (buttonType == HomeButtonType.Start) {
        Pulsating(duration = 2000, pulseFraction = 1.1f) {
            boxContent()
        }
    } else {
        boxContent()
    }

    val height = if (buttonType == HomeButtonType.Start) 20.dp else 10.dp
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun CircledButtonStart(
    buttonType: HomeButtonType,
    onClick: () -> Unit
) {
    val boxContent = @Composable {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(55.dp)
                .background(CustomBlue.copy(alpha = 0.6f), CircleShape)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = CircleShape
                )
                .clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            if (buttonType == HomeButtonType.Settings) {
                Image(
                    painter = painterResource(id = R.drawable.ic_settings),
                    modifier = Modifier.size(35.dp),
                    contentDescription = null,
                )
            }

            if (buttonType == HomeButtonType.NewVersion) {
                Text(
                    text = "¡Nueva\nversión!",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = fredokaCondensedMedium,
                    lineHeight = 12.sp,
                    color = Color.Yellow
                )
            }
        }
    }

    if (buttonType == HomeButtonType.NewVersion) {
        Pulsating(duration = 1000, pulseFraction = 1.3f) {
            boxContent()
        }
    } else {
        boxContent()
    }

    Spacer(modifier = Modifier.height(10.dp))
}


enum class HomeButtonType {
    Start,
    Settings,
    About,
    Tutorial,
    NewVersion
}

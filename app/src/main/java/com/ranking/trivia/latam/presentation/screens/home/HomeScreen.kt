package com.ranking.trivia.latam.presentation.screens.home

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.presentation.theme.CustomBlue
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
import com.ranking.trivia.latam.presentation.utils.sidePadding
import com.ranking.trivia.latam.presentation.utils.verifyNewerVersion

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToPlayScreen: () -> Unit
) {
    val context = LocalContext.current

    var showAboutDialog by remember { mutableStateOf(false) }
    var showOptionsDialog by remember { mutableStateOf(false) }
    var showTutorialDialog by remember { mutableStateOf(false) }

    AboutDialog(showAboutDialog) { showAboutDialog = false }

    OptionsDialog(showOptionsDialog, viewModel) { showOptionsDialog = false }

    TutorialDialog(showTutorialDialog) { showTutorialDialog = false }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeBackground(
            context = context,
            modifierVersion = Modifier.align(Alignment.BottomEnd),
            modifierAdmob = Modifier.align(Alignment.BottomCenter),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .sidePadding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.weight(.5f)) {

                HomeLogoLetters(
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                HomeNewVersionAndSettings(
                    context = context,
                    viewModel = viewModel,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onSettingsClicked = { showOptionsDialog = true },
                )
            }

            Column(
                modifier = Modifier.weight(.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HomeYellowButton(
                    buttonType = HomeButtonType.Start,
                    onClick = { onNavigateToPlayScreen() },
                    content = {
                        TextHomeYellowButton(
                            modifier = Modifier.padding(vertical = 15.dp, horizontal = 60.dp),
                            text = stringResource(R.string.home_start),
                            fontSize = 34.sp
                        )
                    }
                )

                HomeYellowButton(
                    buttonType = HomeButtonType.Tutorial,
                    onClick = { showTutorialDialog = true },
                    content = {
                        TextHomeYellowButton(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 30.dp),
                            text = stringResource(R.string.home_how_to_play),
                            fontSize = 26.sp
                        )
                    }
                )

                HomeYellowButton(
                    buttonType = HomeButtonType.About,
                    onClick = { showAboutDialog = true },
                    content = {
                        TextHomeYellowButton(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 30.dp),
                            text = stringResource(R.string.home_about),
                            fontSize = 26.sp
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun HomeBackground(
    @SuppressLint("ModifierParameter")
    modifierVersion: Modifier,
    modifierAdmob: Modifier,
    context: Context
) {
    Image(
        painter = painterResource(id = R.drawable.pyramid_main_screen),
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        contentDescription = null,
    )

    VignetteInverseEffect(Modifier.fillMaxSize())

    Text(
        modifier = modifierVersion.padding(12.dp),
        text = stringResource(R.string.home_version_number,
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        ),
        color = Color.White,
        fontFamily = fredokaCondensedSemiBold,
        fontSize = 18.sp,
    )
    AdmobBanner(modifier = modifierAdmob)
}

@Composable
fun AdmobBanner(modifier: Modifier) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = "ca-app-pub-3940256099942544/9214589741" // todo: change for the real one
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

@Composable
fun HomeLogoLetters(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.logo_no_background_letters),
        modifier = modifier
            .height(250.dp)
            .fillMaxWidth(),
        contentScale = ContentScale.Fit,
        contentDescription = null,
    )
}


@Composable
fun HomeNewVersionAndSettings(
    modifier: Modifier,
    context: Context,
    viewModel: HomeViewModel,
    onSettingsClicked: () -> Unit,
) {
    var showNewerVersionButton by remember { mutableStateOf(false) }
    verifyNewerVersion(viewModel, context) { showNewerVersionButton = !it }

    Column(modifier = modifier) {
        if (showNewerVersionButton) {
            CircledButtonStart(HomeButtonType.NewVersion) {
                // todo: real URL from google play
                openUrl(context, "https://play.google.com/store/games")
            }
        }
        CircledButtonStart(HomeButtonType.Settings) {
            onSettingsClicked()
        }
    }
}


@Composable
fun HomeYellowButton(
    viewModel: HomeViewModel = hiltViewModel(),
    buttonType: HomeButtonType,
    playSound: Boolean = true,
    onClick: () -> Unit,
    content: @Composable (() -> Unit)
) {
    val context = LocalContext.current

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
fun TextHomeYellowButton(
    modifier: Modifier,
    text: String,
    fontSize: TextUnit,
) {
    Text(
        text = text,
        fontSize = fontSize,
        textAlign = TextAlign.Center,
        fontFamily = fredokaCondensedSemiBold,
        color = Color.DarkGray,
        style = TextStyle(shadow = regularShadow),
        modifier = modifier
    )
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
                    text = stringResource(R.string.home_new_version),
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

package com.ranking.trivia.latam.presentation.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.presentation.screens.home.HomeButtonType
import com.ranking.trivia.latam.presentation.screens.home.HomeYellowButton
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedSemiBold

@Composable
fun ResetPrefsDialog(
    isVisible: Boolean,
    onResetClicked: () -> Unit
) {
    if (isVisible) {
        BaseDialog(
            title = "Juego completado",
            onDismiss = { },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_info),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = stringResource(R.string.reset_prefs_description),
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = fredokaCondensedSemiBold,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        HomeYellowButton(
                            playSound = false,
                            buttonType = HomeButtonType.About,
                            onClick = onResetClicked,
                            content = {
                                Text(
                                    text = "Comenzar nueva partida",
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    fontFamily = fredokaCondensedSemiBold,
                                    color = Color.DarkGray,
                                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp)
                                )
                            }
                        )
                    }
                }
            }
        )
    }
}


@Preview
@Composable
fun ResetPrefsDialogPreview() {
    ResetPrefsDialog(
        isVisible = true,
        onResetClicked = { }
    )
}

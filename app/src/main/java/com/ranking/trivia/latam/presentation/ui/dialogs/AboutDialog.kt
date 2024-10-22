package com.ranking.trivia.latam.presentation.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.presentation.screens.home.HomeYellowButton
import com.ranking.trivia.latam.presentation.screens.home.HomeButtonType
import com.ranking.trivia.latam.presentation.theme.CustomBlue
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedMedium
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedSemiBold

@Composable
fun AboutDialog(
    isVisible: Boolean,
    onExitClicked: () -> Unit
) {
    if (isVisible) {
        BaseDialog(
            title = "Acerca de",
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

                    ExpandableText(text = stringResource(id = R.string.about_disclaimer))
                    Spacer(modifier = Modifier.height(12.dp))
                    ExpandableText(text = stringResource(id = R.string.about_ads))
                    Spacer(modifier = Modifier.height(12.dp))
                    ExpandableText(text = stringResource(id = R.string.about_ui_and_sounds))
                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        HomeYellowButton(
                            playSound = false,
                            buttonType = HomeButtonType.About,
                            onClick = onExitClicked,
                            content = {
                                Text(
                                    text = "Salir",
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

@Composable
fun ExpandableText(text: String) {
    val maxLines = 1
    var expandedState by remember { mutableStateOf(false) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    Column(modifier = Modifier.fillMaxWidth()) {
        val readMore = "Leer más"
        val readLess = "Leer menos"
        Text(
            text = text,
            maxLines = if (expandedState) Int.MAX_VALUE else maxLines,
            fontSize = 16.sp,
            fontFamily = fredokaCondensedMedium,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult = it },
            modifier = Modifier.clickable { expandedState = !expandedState }
        )
        // 44 characters
        if (text.length > 44) {
            Text(
                text = if (expandedState) readLess else readMore,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                fontFamily = fredokaCondensedMedium,
                color = CustomBlue,
                modifier = Modifier
                    .align(Alignment.Start)
                    .clickable { expandedState = !expandedState }
            )
        }
    }
}


@Preview
@Composable
fun AboutDialogPreview() {
    AboutDialog(
        isVisible = true,
        onExitClicked = { }
    )
}

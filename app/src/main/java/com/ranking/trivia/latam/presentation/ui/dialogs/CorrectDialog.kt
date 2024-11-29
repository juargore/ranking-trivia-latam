package com.ranking.trivia.latam.presentation.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.presentation.theme.CustomBlue
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedBold
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedMedium
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedSemiBold
import com.ranking.trivia.latam.presentation.theme.lightShadow
import com.ranking.trivia.latam.presentation.theme.strongShadow

@Composable
fun CorrectDialog(
    isVisible: Boolean,
    moreInfo: String?,
    onNextClicked: () -> Unit
) {
    if (isVisible) {

        var showFunFactDialog by remember { mutableStateOf(false) }

        FunFactDialog(
            isVisible = showFunFactDialog,
            info = moreInfo,
            onWowClicked = { showFunFactDialog = false }
        )

        BaseDialog(
            title = stringResource(id = R.string.correct_title),
            onDismiss = { },
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_correct),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(id = R.string.correct_description),
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = fredokaCondensedBold,
                        color = Color.White,
                        lineHeight = 24.sp,
                        style = TextStyle(shadow = strongShadow),
                        modifier = Modifier.wrapContentSize()
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    if (!moreInfo.isNullOrEmpty()) {
                        FunFactView(
                            text = moreInfo.substringAfter("${stringResource(R.string.interesting_fact)}: "),
                            onClick = { showFunFactDialog = true }
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ButtonExitOrRetry(
                            modifier = Modifier,
                            onClick = { onNextClicked() },
                            content = {
                                Text(
                                    text = stringResource(id = R.string.general_next),
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center,
                                    fontFamily = fredokaCondensedSemiBold,
                                    color = Color.DarkGray,
                                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 20.dp)
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
fun FunFactView(text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                CustomBlue.copy(alpha = 0.4f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text =  stringResource(id = R.string.interesting_fact),
                fontSize = 26.sp,
                textAlign = TextAlign.Start,
                fontFamily = fredokaCondensedSemiBold,
                color = Color.White,
                style = TextStyle(shadow = lightShadow),
                modifier = Modifier.align(Alignment.CenterStart)
            )

            Text(
                text = stringResource(id = R.string.about_read_more),
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                fontFamily = fredokaCondensedMedium,
                color = Color.Yellow,
                style = TextStyle(shadow = lightShadow),
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }


        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(55.dp)
                    .background(CustomBlue.copy(alpha = 0.6f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_border_circled_button),
                    modifier = Modifier.size(55.dp),
                    contentDescription = null,
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_bulb),
                    modifier = Modifier.size(40.dp),
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = text,
                fontSize = 19.sp,
                textAlign = TextAlign.Start,
                fontFamily = fredokaCondensedSemiBold,
                color = Color.White,
                style = TextStyle(shadow = lightShadow),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}

@Preview
@Composable
fun CorrectDialogPreview() {
    CorrectDialog(
        isVisible = true,
        moreInfo = "Curiosidad: Descripción del dato aquí para ver qué show y tener una descripción más larga para que aparezca abajo",
        onNextClicked = {

        }
    )
}

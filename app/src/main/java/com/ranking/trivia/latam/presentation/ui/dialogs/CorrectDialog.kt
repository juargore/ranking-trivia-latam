package com.ranking.trivia.latam.presentation.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.domain.models.FlagId.AR
import com.ranking.trivia.latam.domain.models.FlagId.BR
import com.ranking.trivia.latam.domain.models.FlagId.UY
import com.ranking.trivia.latam.domain.models.Question
import com.ranking.trivia.latam.domain.models.QuestionLevel.I
import com.ranking.trivia.latam.presentation.theme.CustomBlue
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedBold
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedSemiBold
import com.ranking.trivia.latam.presentation.theme.strongShadow

@Composable
fun CorrectDialog(
    isVisible: Boolean,
    question: Question?,
    onNextClicked: () -> Unit
) {
    if (isVisible) {
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

                    Spacer(modifier = Modifier.height(30.dp))

                    if (!question?.moreInfo.isNullOrEmpty()) {
                        Text(
                            text =  stringResource(id = R.string.interesting_fact),
                            fontSize = 19.sp,
                            textAlign = TextAlign.Start,
                            fontFamily = fredokaCondensedSemiBold,
                            color = CustomBlue,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        ExpandableText(text = question!!.moreInfo!!)
                    }

                    Spacer(modifier = Modifier.height(35.dp))

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


@Preview
@Composable
fun CorrectDialogPreview() {
    CorrectDialog(
        isVisible = true,
        question = Question(11, I, "Pregunta 1", listOf(BR, AR, UY), "Descripción del dato aquí para ver qué show y tener una descripción más larga para que aparezca abajo"),
        onNextClicked = {

        }
    )
}

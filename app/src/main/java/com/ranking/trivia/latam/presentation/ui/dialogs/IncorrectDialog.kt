package com.ranking.trivia.latam.presentation.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedBold
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedSemiBold
import com.ranking.trivia.latam.presentation.theme.strongShadow

@Composable
fun IncorrectDialog(
    isVisible: Boolean,
    onRetryClicked: () -> Unit,
    onExitClicked: () -> Unit
) {
    if (isVisible) {
        BaseDialog(
            title = "Incorrecto!",
            onDismiss = { },
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Haz ordenado de forma incorrecta el ranking de las banderas.\nPero no te preocupes, estás muy cerca de lograrlo!!\n\nInténtalo nuevamente!",
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = fredokaCondensedBold,
                        color = Color.White,
                        lineHeight = 24.sp,
                        style = TextStyle(shadow = strongShadow),
                        modifier = Modifier.wrapContentSize()
                    )

                    Spacer(modifier = Modifier.height(35.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ButtonExitOrRetry(
                            modifier = Modifier.weight(0.5f),
                            onClick = { onExitClicked() },
                            content = {
                                Text(
                                    text = "Salir",
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center,
                                    fontFamily = fredokaCondensedSemiBold,
                                    color = Color.DarkGray,
                                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 20.dp)
                                )
                            }
                        )

                        ButtonExitOrRetry(
                            modifier = Modifier.weight(0.5f),
                            onClick = { onRetryClicked() },
                            content = {
                                Text(
                                    text = "Reintentar",
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
fun IncorrectDialogPreview() {
    IncorrectDialog(
        isVisible = true,
        onRetryClicked = {},
        onExitClicked = {}
    )
}
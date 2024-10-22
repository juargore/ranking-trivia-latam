package com.ranking.trivia.latam.presentation.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun TimeUpDialog(
    isVisible: Boolean,
    onRetryClicked: () -> Unit
) {
    if (isVisible) {
        BaseDialog(
            onDismiss = { },
            title = "Se terminó el tiempo!",
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Necesitas apresurarte para lograr acomodar correctamente las banderas en el orden correcto.\n\nVenga! Tú puedes!!",
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
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ButtonExitOrRetry(
                            modifier = Modifier,
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

@Composable
fun ButtonExitOrRetry(
    modifier: Modifier,
    onClick: () -> Unit,
    content: @Composable (() -> Unit)
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .padding(horizontal = 5.dp)
            .background(
                color = Color.Yellow,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 3.dp,
                color = Color.Black,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Preview
@Composable
fun TimeUpDialogPreview() {
    TimeUpDialog(
        isVisible = true,
        onRetryClicked = {}
    )
}

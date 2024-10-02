package com.ranking.trivia.latam.presentation.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.ranking.trivia.latam.presentation.theme.CustomBlue
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedBold

@Composable
fun BaseDialog(
    title: String,
    content: @Composable () -> Unit,
    onDismiss: () -> Unit,
) {
    val allRoundedCornerShape = RoundedCornerShape(10.dp)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.82f)
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(horizontal = 15.dp)
        ) {

            Box(
                modifier = Modifier
                    .padding(top = 17.dp) // transparent space top icon
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CustomBlue.copy(alpha = 0.9f), shape = allRoundedCornerShape)
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 10.dp, top = 30.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(alpha = 0.8f), shape = allRoundedCornerShape)
                            .padding(horizontal = 12.dp)
                            .padding(bottom = 10.dp, top = 10.dp)
                    ) {
                        content()
                    }
                }
            }

            Box(
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .background(Color.Yellow, shape = RoundedCornerShape(20.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(20.dp))
                ) {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = fredokaCondensedBold,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(
                            vertical = 5.dp,
                            horizontal = 18.dp
                        )
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun BaseDialogPreview() {
    BaseDialog(
        title = "Opciones",
        content = {

        },
        onDismiss = {

        }
    )
}

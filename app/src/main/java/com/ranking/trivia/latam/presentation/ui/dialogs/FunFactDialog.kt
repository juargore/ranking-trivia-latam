package com.ranking.trivia.latam.presentation.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedSemiBold

@Composable
fun FunFactDialog(
    isVisible: Boolean,
    info: String?,
    onWowClicked: () -> Unit
) {
    if (isVisible) {
        BaseDialog(
            title = stringResource(id = R.string.interesting_fact),
            onDismiss = { },
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FormattedText(info.orEmpty())

                    Spacer(modifier = Modifier.height(25.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ButtonExitOrRetry(
                            modifier = Modifier,
                            onClick = { onWowClicked() },
                            content = {
                                Text(
                                    text = "Wow!",
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
fun FormattedText(text: String) {
    val formattedText = buildAnnotatedString {
        var index = 0
        val funFact = stringResource(id = R.string.interesting_fact)
        while (index < text.length) {
            when {
                text.startsWith("●", index) -> {
                    append("● ")
                    index += 2
                    val endIndex = text.indexOf(":", index).takeIf { it != -1 } ?: text.length
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(text.substring(index, endIndex).trim())
                    }
                    append(": ")
                    index = endIndex + 1
                }

                text.startsWith(funFact, index) -> {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(funFact)
                    }
                    append(": ")
                    index += funFact.length
                }

                text.startsWith("\n", index) -> {
                    val newlineCount = text.substring(index).takeWhile { it == '\n' }.length
                    repeat(newlineCount) { append("\n") }
                    index += newlineCount
                }

                else -> {
                    val nextSpecialIndex = listOf(
                        text.indexOf("●", index),
                        text.indexOf(funFact, index),
                        text.indexOf("\n", index)
                    ).filter { it != -1 }.minOrNull() ?: text.length
                    append(text.substring(index, nextSpecialIndex))
                    index = nextSpecialIndex
                }
            }
        }
    }

    Text(
        text = formattedText,
        textAlign = TextAlign.Start
    )
}


@Preview
@Composable
fun FunFactDialogPreview() {
    FunFactDialog(
        isVisible = true,
        info = """
        ● Jamaica: Tiene la mayor cantidad de medallas olímpicas en atletismo, destacándose especialmente en pruebas de velocidad, con estrellas como Usain Bolt, quien ha ganado 8 medallas de oro.
        \n\n● Cuba: Ha obtenido un número significativo de medallas en atletismo, con un enfoque en eventos de salto y lanzamiento, acumulando un total de 30 medallas en los Juegos Olímpicos.
        \n\n● Puerto Rico: Aunque ha logrado destacar en algunos eventos, especialmente en salto con garrocha, su total de medallas olímpicas en atletismo es menor, con 9 medallas.
        \n\nCuriosidad: Jamaica es famosa por su dominio en pruebas de velocidad, especialmente en los Juegos Olímpicos, donde sus atletas han batido récords mundiales y han dejado una huella indeleble en la historia del atletismo.
        """.trimIndent(),
        onWowClicked = { }
    )
}

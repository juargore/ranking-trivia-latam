package com.ranking.trivia.latam.presentation.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.presentation.theme.CustomBlue
import com.ranking.trivia.latam.presentation.theme.CustomGreen
import com.ranking.trivia.latam.presentation.theme.Orange
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedBold
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedSemiBold
import com.ranking.trivia.latam.presentation.theme.strongShadow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun OptionsDialog(
    isVisible: Boolean,
    isSoundEnabled: Boolean,
    onSaveClicked: (enableSound: Boolean) -> Unit,
    onExitClicked: () -> Unit
) {
    var soundEnabled by remember { mutableStateOf(isSoundEnabled) }

    if (isVisible) {
        BaseDialog(
            title = "Opciones",
            onDismiss = { },
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 18.dp)
                    ) {
                        CustomSwitch(
                            height = 40.dp,
                            width = 70.dp,
                            circleButtonPadding = 4.dp,
                            stateOn = 1,
                            stateOff = 0,
                            initialValue = if (soundEnabled) 1 else 0,
                            onCheckedChanged = { checked ->
                                soundEnabled = checked
                                println("AQUI: Valor de soundEnabled = $soundEnabled")
                            }
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            text = "Sonido",
                            fontSize = 28.sp,
                            textAlign = TextAlign.Start,
                            fontFamily = fredokaCondensedBold,
                            color = Color.White,
                            style = TextStyle(shadow = strongShadow)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_sound_ii),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    RadioButtonSample()

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ButtonExitOrRetry(
                            modifier = Modifier.weight(0.5f),
                            onClick = { onExitClicked() },
                            content = {
                                Text(
                                    text = "Salir",
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    fontFamily = fredokaCondensedSemiBold,
                                    color = Color.DarkGray,
                                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 20.dp)
                                )
                            }
                        )

                        ButtonExitOrRetry(
                            modifier = Modifier.weight(0.5f),
                            onClick = {
                                println("AQUI: Valor de soundEnabled en onSave() = $soundEnabled")
                                onSaveClicked(soundEnabled)
                                      },
                            content = {
                                Text(
                                    text = "Guardar",
                                    fontSize = 20.sp,
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

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun CustomSwitch(
    height: Dp,
    width: Dp,
    circleButtonPadding: Dp,
    stateOn: Int,
    stateOff: Int,
    initialValue: Int,
    onCheckedChanged: (checked: Boolean) -> Unit
) {
    val swipeableState = rememberSwipeableState(
        initialValue = initialValue,
        confirmStateChange = { newState ->
            if (newState == stateOff) {
                onCheckedChanged(false)
            } else {
                onCheckedChanged(true)
            }
            true
        }
    )

    val sizePx = with(LocalDensity.current) { (width - height).toPx() }
    val anchors = mapOf(0f to stateOff, sizePx to stateOn)

    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .height(height)
            .width(width)
            .clip(RoundedCornerShape(height))
            .border(2.dp, Color.DarkGray, CircleShape)
            .background(
                if (swipeableState.currentValue == stateOff) Color.DarkGray else Orange
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                )
                .size(height)
                .padding(circleButtonPadding)
                .border(1.dp, Color.DarkGray, CircleShape)
                .clip(RoundedCornerShape(50))
                .background(
                    if (swipeableState.currentValue == stateOff) {
                        Color.LightGray
                    } else {
                        CustomGreen
                    }
                )
                .clickable {
                    scope.launch {
                        if (swipeableState.currentValue == stateOff) {
                            swipeableState.animateTo(stateOn)
                            onCheckedChanged(true)
                        } else {
                            swipeableState.animateTo(stateOff)
                            onCheckedChanged(false)
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (swipeableState.currentValue == stateOff) {
                    "Off"
                } else {
                    "On"
                },
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontFamily = fredokaCondensedBold,
                color = Color.White,
                lineHeight = 26.sp,
                style = TextStyle(shadow = strongShadow),
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}

@Composable
fun RadioButtonSample() {

    val radioOptions = listOf("Español", "Inglés", "Portugés")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1] ) }

    Column {
        Text(
            text = "Idioma",
            fontSize = 28.sp,
            textAlign = TextAlign.Start,
            fontFamily = fredokaCondensedBold,
            color = Color.White,
            style = TextStyle(shadow = strongShadow),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp)
        )
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        }
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    colors = RadioButtonColors(
                        selectedColor = CustomBlue,
                        unselectedColor = Color.Gray,
                        disabledSelectedColor = Color.Gray,
                        disabledUnselectedColor = Color.Black
                    ),
                    onClick = { onOptionSelected(text) },
                )
                Text(
                    text = text,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = fredokaCondensedBold,
                    color = Color.White,
                    style = TextStyle(shadow = strongShadow)
                )
            }
        }
    }
}

@Preview
@Composable
fun OptionsDialogPreview() {
    OptionsDialog(
        isVisible = true,
        isSoundEnabled = true,
        onSaveClicked = { },
        onExitClicked = { }
    )
}

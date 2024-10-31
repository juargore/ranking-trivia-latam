package com.ranking.trivia.latam.presentation.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.presentation.screens.home.HomeViewModel
import com.ranking.trivia.latam.presentation.screens.play.PlayViewModel
import com.ranking.trivia.latam.presentation.theme.CustomBlue
import com.ranking.trivia.latam.presentation.theme.CustomGreen
import com.ranking.trivia.latam.presentation.theme.Orange
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedBold
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedSemiBold
import com.ranking.trivia.latam.presentation.theme.lightShadow
import com.ranking.trivia.latam.presentation.theme.strongShadow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun HintDescriptionDialog(
    isVisible: Boolean,
    onAcceptClicked: (Boolean) -> Unit,
    onCancelClicked: () -> Unit
) {
    if (isVisible) {

        var checkedState by remember { mutableStateOf(false) }

        BaseDialog(
            title = stringResource(id = R.string.hint_title),
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
                            painter = painterResource(id = R.drawable.ic_bulb),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(id = R.string.hint_description),
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = fredokaCondensedBold,
                        color = Color.White,
                        lineHeight = 24.sp,
                        style = TextStyle(shadow = strongShadow),
                        modifier = Modifier.wrapContentSize()
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            modifier = Modifier.size(20.dp),
                            checked = checkedState,
                            onCheckedChange = {
                                checkedState = it
                            }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(id = R.string.hint_checkbox),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = fredokaCondensedSemiBold,
                            color = Color.White,
                            style = TextStyle(shadow = lightShadow),
                            modifier = Modifier.wrapContentSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ButtonExitOrRetry(
                            modifier = Modifier.weight(0.5f),
                            onClick = { onCancelClicked() },
                            content = {
                                Text(
                                    text = stringResource(id = R.string.general_cancel),
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
                                onAcceptClicked(checkedState)
                            },
                            content = {
                                Text(
                                    text = stringResource(id = R.string.general_accept),
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


@Preview
@Composable
fun HintDescriptionDialogPreview() {
    HintDescriptionDialog(
        isVisible = true,
        onAcceptClicked = {},
        onCancelClicked = {}
    )
}

package com.ranking.trivia.latam.presentation.ui.dialogs

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.LocalTextStyle
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.domain.models.TriviaFlag
import com.ranking.trivia.latam.presentation.screens.hall.HallOfFameViewModel
import com.ranking.trivia.latam.presentation.screens.home.HomeButtonType
import com.ranking.trivia.latam.presentation.screens.home.HomeYellowButton
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedBold
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedMedium
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedSemiBold
import com.ranking.trivia.latam.presentation.theme.strongShadow
import com.ranking.trivia.latam.presentation.utils.isInternetConnected
import com.ranking.trivia.latam.presentation.utils.showToast
import com.ranking.trivia.latam.presentation.utils.sidePadding

@Composable
fun SaveRankingDialog(
    isVisible: Boolean,
    viewModel: HallOfFameViewModel,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var word by rememberSaveable { mutableStateOf("") }
    var triviaFlag: TriviaFlag? by remember { mutableStateOf(null) }

    if (isVisible) {
        BaseDialog(
            title = stringResource(R.string.new_record_title),
            onDismiss = { },
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ScoreUI(score = viewModel.getTotalScore())

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = stringResource(R.string.new_record_description_top),
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = fredokaCondensedSemiBold,
                        color = Color.DarkGray
                    )

                    TextFieldTrivia(
                        allFlags = viewModel.getAllTriviaFlags(),
                        onWordChange = { word = it },
                        onFlagSelected = { triviaFlag = it }
                    )

                    Text(
                        text = stringResource(R.string.new_record_description_bottom),
                        fontSize = 15.sp,
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = fredokaCondensedSemiBold,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        HomeYellowButton(
                            playSound = false,
                            buttonType = HomeButtonType.About,
                            onClick = {
                                if (isInternetConnected(context)) {
                                    if (word.isNotEmpty()) {
                                        if (triviaFlag != null) {
                                            viewModel.saveNewRecord(triviaFlag, word)
                                            showToast(context, context.getString(R.string.new_record_saving))
                                            onDismiss()
                                        } else {
                                            showToast(context, context.getString(R.string.new_record_select_flag))
                                        }
                                    } else {
                                        showToast(context, context.getString(R.string.new_record_write_a_name))
                                    }
                                } else {
                                    showToast(context, context.getString(R.string.new_record_need_internet))
                                }
                            },
                            content = {
                                Text(
                                    text = stringResource(R.string.new_record_save),
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
fun ScoreUI(score: Int) {
    val context = LocalContext.current
    val scoreString = "%,d".format(score)
    val sizes = listOf(20.sp, 26.sp, 30.sp, 26.sp, 20.sp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentWidth()
            .height(50.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .background(Color.Red, CircleShape)
                .clickable {
                    Toast
                        .makeText(context, context.getString(R.string.new_record_your_current_score), Toast.LENGTH_SHORT)
                        .show()
                }
            ,
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_border_circled_button),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
            )
        }

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            scoreString.forEachIndexed { index, char ->
                val fontSize = sizes[index % sizes.size]
                Text(
                    text = char.toString(),
                    fontSize = fontSize,
                    color = Color.White,
                    fontFamily = fredokaCondensedBold,
                    style = TextStyle(shadow = strongShadow),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
fun TextFieldTrivia(
    allFlags: List<TriviaFlag>,
    onWordChange: (String) -> Unit,
    onFlagSelected: (TriviaFlag) -> Unit
) {
    var word by rememberSaveable { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 15.dp)
    ) {
        // flag section
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(.2f)
        ) {
            FlagsDropDownMenu(allFlags) { flag ->
                onFlagSelected(flag)
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // input text for name as string
        Row(
            modifier = Modifier
                .height(50.dp)
                .weight(.8f)
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                .sidePadding()
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = word,
                onValueChange = {
                    word = it
                    onWordChange(word)
                },
                singleLine = false,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Start,
                    fontFamily = fredokaCondensedMedium,
                    fontSize = 18.sp
                )
            )
        }
    }
}

@Composable
fun FlagsDropDownMenu(
    allFlags: List<TriviaFlag>,
    onFlagSelected: (TriviaFlag) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableIntStateOf(allFlags.first().image) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded = true }) {
            Image(
                painter = painterResource(id = selectedImage),
                contentDescription = null,
                modifier = Modifier.height(50.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            allFlags.forEach { flag ->
                DropdownMenuItem(
                    text = {
                        Image(
                            modifier = Modifier
                                .size(45.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            painter = painterResource(flag.image),
                            contentDescription = null
                        )
                    },
                    onClick = {
                        onFlagSelected(flag)
                        selectedImage = flag.image
                        expanded = false
                    }
                )
            }
        }
    }
}

package com.ranking.trivia.latam.presentation.screens.play

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.domain.models.EmptySpace
import com.ranking.trivia.latam.domain.models.TriviaFlag
import com.ranking.trivia.latam.presentation.theme.CustomBlue
import com.ranking.trivia.latam.presentation.theme.CustomGreen
import com.ranking.trivia.latam.presentation.theme.Orange
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedBold
import com.ranking.trivia.latam.presentation.theme.regularShadow
import com.ranking.trivia.latam.presentation.utils.DragTarget
import com.ranking.trivia.latam.presentation.utils.DropTarget
import com.ranking.trivia.latam.presentation.utils.playSound

@Composable
fun CardEmptySpace(
    index: Int,
    emptySpace: EmptySpace,
    viewModel: PlayViewModel
) {
    val context = LocalContext.current

    DropTarget<TriviaFlag>(
        modifier = Modifier
    ) { isInBound, flag ->

        val backgroundColor = if (isInBound && emptySpace.flag == null) Color.Red else Color.White
        val borderWidth = if (isInBound && emptySpace.flag == null) 4.dp else 1.5.dp

        flag?.let {
            if (isInBound && emptySpace.flag == null && !viewModel.isThisFlagAlreadyUsed(flag)) {
                viewModel.updateEmptySpaceUI(emptySpace.id, flag)
                viewModel.removeFlagFromList(flag)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            val icon = when (index) {
                0 -> R.drawable.ic_medal_gold
                1 -> R.drawable.ic_medal_silver
                2 -> R.drawable.ic_medal_bronze
                else -> null
            }

            // medal of honor
            Box(
                modifier = Modifier
                    .background(
                        color = CustomBlue.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                if (icon != null) {
                    Image(
                        painter = painterResource(id = icon),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .height(100.dp)
                    .width(120.dp)
                    .padding(vertical = 2.dp)
                    .background(backgroundColor, shape = RoundedCornerShape(12.dp))
                    .border(borderWidth, Color.Black, RoundedCornerShape(12.dp))
            ) {
                emptySpace.flag?.let {
                    CardFlag(flag = it, enableDragging = false)
                } ?: run {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "${index + 1}°",
                        fontSize = 28.sp,
                        fontFamily = fredokaCondensedBold,
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                }
            }

            val iconRemoveSize = 30.dp
            Spacer(modifier = Modifier.width(5.dp))
            Box(modifier = Modifier.size(iconRemoveSize + 4.dp)) {
                if (emptySpace.flag != null) {
                    Box(
                        modifier = Modifier
                            .size(iconRemoveSize)
                            .clip(CircleShape)
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_remove_filled),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(color = Orange),
                            modifier = Modifier
                                .size(iconRemoveSize)
                                .clickable {
                                    if (viewModel.shouldPlaySound()) {
                                        playSound(context, R.raw.sound_remove)
                                    }
                                    viewModel.addFlagToList(emptySpace.flag)
                                    viewModel.updateEmptySpaceUI(emptySpace.id, null)
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardFlag(
    flag: TriviaFlag,
    enableDragging: Boolean = true
) {
    @Composable
    fun CardContent() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (flag.alreadyPlayed) Color.Transparent else CustomGreen.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp)
                )
                .border(1.5.dp, Color.Black, RoundedCornerShape(12.dp))
        ) {
            // country flag + bottom name
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!flag.alreadyPlayed) {
                    Image(
                        painter = painterResource(id = flag.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(80.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, Color.DarkGray, RoundedCornerShape(10.dp))
                    )
                    Text(
                        text = flag.name,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontFamily = fredokaCondensedBold,
                        color = Color.White,
                        style = TextStyle(shadow = regularShadow),
                        lineHeight = 16.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            // lock icon at right|bottom
            /*Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_lock),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }*/
        }
    }

    val modifier = Modifier
        .height(110.dp)
        .width(120.dp)

    if (enableDragging) {
        DragTarget(
            modifier = modifier.padding(vertical = 2.dp),
            dataToDrop = flag,
        ) {
            Card(
                modifier = modifier,
                elevation = CardDefaults.cardElevation(14.dp),
                shape = RoundedCornerShape(12.dp),
                content = { CardContent() }
            )
        }
    } else {
        CardContent()
    }
}

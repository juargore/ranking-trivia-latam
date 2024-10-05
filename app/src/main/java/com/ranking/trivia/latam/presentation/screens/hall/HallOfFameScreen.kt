package com.ranking.trivia.latam.presentation.screens.hall

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.presentation.screens.home.AdmobBanner
import com.ranking.trivia.latam.presentation.screens.play.HeaderBackAndCategory
import com.ranking.trivia.latam.presentation.theme.CustomBlue
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedMedium
import com.ranking.trivia.latam.presentation.theme.lightShadow
import com.ranking.trivia.latam.presentation.ui.dialogs.SaveRankingDialog
import com.ranking.trivia.latam.presentation.utils.HALL_BOTTOM_SMALL_BANNER_ID
import com.ranking.trivia.latam.presentation.utils.VignetteInverseEffect
import com.ranking.trivia.latam.presentation.utils.sidePadding

@Composable
fun HallOfFameScreen(
    viewModel: HallOfFameViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val rankingList by viewModel.ranking.collectAsState()
    var showNewRankingDialog by remember { mutableStateOf(false) }

    SaveRankingDialog(showNewRankingDialog, viewModel) {
        showNewRankingDialog = false
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HallOfFameBackground(Modifier.align(Alignment.BottomCenter))

        Column(modifier = Modifier
            .fillMaxSize()
            .sidePadding(12.dp)
            .padding(bottom = 55.dp)
        ) {
            HeaderBackAndCategory { onBack() }

            Spacer(modifier = Modifier.height(10.dp))

            HallOfFameTitle()
            
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = CustomBlue.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(rankingList) { i, ranking ->
                    RankingCard(ranking.also {
                        it.position = i + 1
                    })
                }
            }
        }

        // TODO: Delete FAB for PROD
        /*
        FloatingButtonV2(
            iconVector = Icons.Filled.Add,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            showNewRankingDialog = true
        }
        */
    }
}

@Composable
fun HallOfFameTitle() {
    Column(
        modifier = Modifier
            .background(
                color = CustomBlue.copy(alpha = 0.6f),
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(10.dp)
    ) {
        Text(
            text = stringResource(R.string.hof_title),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            fontFamily = fredokaCondensedMedium,
            color = Color.White,
            lineHeight = 24.sp,
            style = TextStyle(shadow = lightShadow),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.hof_description),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontFamily = fredokaCondensedMedium,
            color = Color.White,
            lineHeight = 24.sp,
            style = TextStyle(shadow = lightShadow),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun HallOfFameBackground(
    @SuppressLint("ModifierParameter")
    modifierAdmob: Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.pyramid_two),
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        contentDescription = null,
    )

    VignetteInverseEffect(Modifier.fillMaxSize())

    AdmobBanner(
        modifier = modifierAdmob,
        adId = HALL_BOTTOM_SMALL_BANNER_ID
    )
}

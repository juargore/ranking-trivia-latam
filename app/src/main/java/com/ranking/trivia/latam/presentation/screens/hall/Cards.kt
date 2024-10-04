package com.ranking.trivia.latam.presentation.screens.hall

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ranking.trivia.latam.domain.models.Ranking
import com.ranking.trivia.latam.presentation.theme.fredokaCondensedMedium
import com.ranking.trivia.latam.presentation.theme.lightShadow

@Composable
fun RankingCard(
    ranking: Ranking
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(.1f)
        ) {
            Text(
                text = ranking.position.toString(),
                fontSize = 18.sp,
                fontFamily = fredokaCondensedMedium,
                color = Color.White,
                lineHeight = 24.sp,
                style = TextStyle(shadow = lightShadow)
            )
        }
        Box(
            modifier = Modifier.weight(.15f)
        ) {
            ranking.flag?.image?.let {
                Image(
                    painter = painterResource(it),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                )
            }
        }
        Box(
            modifier = Modifier.weight(.6f)
        ) {
            Text(
                text = ranking.user_name,
                fontSize = 20.sp,
                fontFamily = fredokaCondensedMedium,
                color = Color.White,
                lineHeight = 24.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(shadow = lightShadow)
            )
        }
        Box(
            modifier = Modifier.weight(.15f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "%,d".format(ranking.score),
                fontSize = 18.sp,
                fontFamily = fredokaCondensedMedium,
                color = Color.White,
                lineHeight = 24.sp,
                style = TextStyle(shadow = lightShadow)
            )
        }
    }
}

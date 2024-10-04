package com.ranking.trivia.latam.presentation.screens.hall

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun FloatingButtonV2(
    modifier: Modifier = Modifier,
    contentColor: Color = Color.Black,
    iconVector: ImageVector? = null,
    icon: Int? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        FloatingActionButton(
            contentColor = contentColor,
            onClick = onClick,
        ) {
            iconVector?.let {
                Icon(it, null)
            }
            icon?.let {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(it),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = contentColor)
                )
            }
        }
    }
}
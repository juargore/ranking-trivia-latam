package com.ranking.trivia.latam.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ranking.trivia.latam.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val fredokaExpandedMedium = FontFamily(Font(R.font.fredoka_expanded_medium))
val fredokaExpandedSemiBold = FontFamily(Font(R.font.fredoka_expanded_semibold))
val fredokaExpandedBold = FontFamily(Font(R.font.fredoka_expanded_bold))
val fredokaCondensedMedium = FontFamily(Font(R.font.fredoka_condensed_medium))
val fredokaCondensedSemiBold = FontFamily(Font(R.font.fredoka_condensed_semibold))
val fredokaCondensedBold = FontFamily(Font(R.font.fredoka_condensed_bold))

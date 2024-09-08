@file:Suppress("unused")

package com.vadmax.timetosleep.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    background = Color.Black,
)

data class AppThemeColors(
    val icon: Color,
)

val darkColors = AppThemeColors(
    icon = Color.White,
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColorPalette,
        content = content,
        typography = AppTypography(),
    )
}

val LocalAppThemeColors = staticCompositionLocalOf { darkColors }
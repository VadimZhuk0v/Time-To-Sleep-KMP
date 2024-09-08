package com.vadmax.timetosleep.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import timetosleepkmp.composeapp.generated.resources.Res
import timetosleepkmp.composeapp.generated.resources.baloo

@Composable
private fun DefaultFontFamily() = FontFamily(
    Font(resource = Res.font.baloo),
)

val Typography.clock
    get() =
        TextStyle(
            fontSize = 40.sp,
        )

@Composable
fun AppTypography() =
    Typography(
        bodyLarge = Typography().bodyLarge.copy(fontFamily = DefaultFontFamily()),
        bodyMedium = Typography().bodyMedium.copy(fontFamily = DefaultFontFamily()),
        bodySmall = Typography().bodySmall.copy(fontFamily = DefaultFontFamily()),
        displayLarge = Typography().displayLarge.copy(fontFamily = DefaultFontFamily()),
        displayMedium = Typography().displayMedium.copy(fontFamily = DefaultFontFamily()),
        displaySmall = Typography().displaySmall.copy(fontFamily = DefaultFontFamily()),
        headlineLarge = Typography().headlineLarge.copy(fontFamily = DefaultFontFamily()),
        headlineMedium = Typography().headlineMedium.copy(fontFamily = DefaultFontFamily()),
        headlineSmall = Typography().headlineSmall.copy(fontFamily = DefaultFontFamily()),
        labelLarge = Typography().labelLarge.copy(fontFamily = DefaultFontFamily()),
        labelMedium = Typography().labelMedium.copy(fontFamily = DefaultFontFamily()),
        labelSmall = Typography().labelSmall.copy(fontFamily = DefaultFontFamily()),
        titleLarge = Typography().titleLarge.copy(fontFamily = DefaultFontFamily()),
        titleMedium = Typography().titleMedium.copy(fontFamily = DefaultFontFamily()),
        titleSmall = Typography().titleSmall.copy(fontFamily = DefaultFontFamily()),
    )

package com.example.myfirstapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF7C3AED),
    secondary = Color(0xFF6D28D9),
    tertiary = Color(0xFF9333EA)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF7C3AED),
    secondary = Color(0xFF6D28D9),
    tertiary = Color(0xFF9333EA)
)

@Composable
fun MyFirstAppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        content = content
    )
}

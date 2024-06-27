package com.eotw95.wantnote.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColors(
    primary = Color.Gray,
    secondary = Color.Gray,
    background = Color.Black,
)

@Composable
fun WantNoteTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colors = colorScheme,
        typography = Typography,
        content = content
    )
}
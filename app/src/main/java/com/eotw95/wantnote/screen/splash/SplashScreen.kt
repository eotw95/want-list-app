package com.eotw95.wantnote.screen.splash

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.eotw95.wantnote.common.composable.ColumnCenterContent
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onAppStart: () -> Unit) {
    SplashScreenContent(onAppStart = onAppStart)
}

@Composable
fun SplashScreenContent(
    modifier: Modifier = Modifier,
    onAppStart: () -> Unit
) {
    ColumnCenterContent { CircularProgressIndicator() }
    LaunchedEffect(key1 = true) {
        delay(1000L)
        onAppStart()
    }
}
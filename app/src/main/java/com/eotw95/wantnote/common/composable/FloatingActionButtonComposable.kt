package com.eotw95.wantnote.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eotw95.wantnote.R

@Composable
fun TopFloatingActionButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { /* do nothing because do action in button's onClick function */ },
        backgroundColor = Color.Transparent,
        // 画面下部にシステムバーがあるタイプの端末だと、FloatingActionButtonお被るのでsystemBarsPaddingを追加、
        modifier = Modifier.padding(top = 15.dp, start = 15.dp, end = 15.dp).systemBarsPadding(),
//        elevation = FloatingActionButtonDefaults.elevation(
//            defaultElevation = 0.dp,
//            pressedElevation = 0.dp,
//            hoveredElevation = 0.dp,
//            focusedElevation = 0.dp
//        )
    ) {
        BasicButton(
            text = R.string.add_item,
            textColor = MaterialTheme.colors.primary,
            buttonBackgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxWidth().background(brush = Brush.linearGradient(colors = listOf(Color(0xFFFF5067), Color(0xFFFE9395)))),
            textModifier = Modifier.padding(vertical = 10.dp)
        ) { onClick() }
    }
}
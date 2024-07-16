package com.eotw95.wantnote.common.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BasicRow(content: @Composable RowScope.() -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        content = content,
        modifier = Modifier.fillMaxWidth().padding(16.dp, 16.dp)
    )
}

@Composable
fun RowHaveClickAction(
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(16.dp, 16.dp).clickable { onClick() },
        content = content
    )
}

@Composable
fun RowCenterHaveClickAction(
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { onClick() },
        content = content
    )
}
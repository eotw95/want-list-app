package com.eotw95.wantnote.common.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eotw95.wantnote.R

@Composable
fun BasicToolBar(
    title: Int = R.string.text_empty,
    navIcon: ImageVector,
    navAction: () -> Unit,
    action: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = title), color = Color.White) },
        navigationIcon = {
            Icon(
                imageVector = navIcon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable { navAction() }
            )
        },
        actions = action,
        backgroundColor = Color.Black.copy(alpha = 0.2f),
        elevation = 0.dp
    )
}
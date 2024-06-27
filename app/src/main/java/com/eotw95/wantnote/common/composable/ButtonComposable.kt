package com.eotw95.wantnote.common.composable

import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.eotw95.wantnote.common.ext.fieldModifier

@Composable
fun BasicButton(
    text: Int,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fieldModifier()
    ) {
        Text(text = stringResource(id = text))
    }
}
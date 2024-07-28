package com.eotw95.wantnote.common.composable

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.eotw95.wantnote.common.ext.fieldModifier

@Composable
fun BasicText(text: Int) {
    Text(
        text = stringResource(id = text),
        modifier = Modifier.fieldModifier(),
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f)
    )
}
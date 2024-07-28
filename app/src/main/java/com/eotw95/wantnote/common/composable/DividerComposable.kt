package com.eotw95.wantnote.common.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eotw95.wantnote.common.ext.fieldModifier

@Composable
fun BasicDivider() {
    Divider(thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp))
}
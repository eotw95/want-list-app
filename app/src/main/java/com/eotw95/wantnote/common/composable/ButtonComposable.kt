package com.eotw95.wantnote.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.eotw95.wantnote.common.ext.fieldModifier

@Composable
fun BasicButton(
    text: Int,
    textColor: Color = MaterialTheme.colors.onPrimary,
    buttonBackgroundColor: Color = MaterialTheme.colors.onPrimary.copy(alpha = 0.1f),
    border: BorderStroke? = null,
    modifier: Modifier = Modifier.fieldModifier(),
    textModifier: Modifier = Modifier.padding(vertical = 5.dp),
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonBackgroundColor
        ),
        modifier = modifier,
        border = border,
        elevation = null,
        shape = CircleShape
    ) {
        Text(text = stringResource(id = text), color = textColor, modifier = textModifier, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SelectTabButton(
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fieldModifier()
    ) {
        Text(text = text, color = MaterialTheme.colors.onPrimary)
    }
}

@Composable
fun LinkButton(
    encoderUrl: String,
    linkText: String,
    onUrlClick: (String) -> Unit,
) {
    OutlinedButton(
        onClick = { onUrlClick(encoderUrl) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.1f)
        ),
        modifier = Modifier.fieldModifier(),
        shape = RoundedCornerShape(50.dp),
        border = null,
        elevation = null
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().fieldModifier(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = linkText, color = MaterialTheme.colors.onPrimary)
            Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)
        }
    }
}
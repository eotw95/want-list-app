package com.eotw95.wantnote.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eotw95.wantnote.common.ext.fieldModifier

@Composable
fun BasicTextField(
    newValue: String,
    onValueChange: (String) -> Unit,
    text: Int,
    maxLine: Int = 5
) {
    OutlinedTextField(
        value = newValue,
        onValueChange = onValueChange,
        placeholder = { Text(text = stringResource(id = text)) },
        singleLine = true,
        modifier = Modifier.fieldModifier(),
        maxLines = maxLine,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            backgroundColor = Color.Transparent,
            placeholderColor = Color.Gray,
            unfocusedIndicatorColor = Color.White,
            unfocusedLabelColor = Color.White
        )
    )
}

@Composable
fun BasicField(
    text: String
) {
    Card(
        modifier = Modifier.fieldModifier(),
        backgroundColor = Color.Black,
        border = BorderStroke(0.dp, Color.Transparent)
    ) {
        Text(text = text, modifier = Modifier.padding(16.dp, 5.dp), color = Color.White)
    }
}

@Composable
fun linkField(
    encoderUrl: String,
    linkText: String,
    label: Int,
    isExist: Boolean,
    onUrlClick: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 0.dp),
        backgroundColor = Color.Black,
        border = BorderStroke(0.dp, Color.Transparent)
    ) {
        RowHaveClickAction(onClick = { if (isExist) onUrlClick(encoderUrl) }) {
            TextField(
                value = linkText,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = stringResource(id = label), color = Color.White) },
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.White,
                    backgroundColor = Color.Gray.copy(alpha = 0.2f),
                    focusedIndicatorColor = Color.White
                ),
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun descField(
    text: String,
    label: Int,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 0.dp),
        backgroundColor = Color.Black,
        border = BorderStroke(0.dp, Color.Transparent)
    ) {
        ColumnCenterContent {
            TextField(
                value = text,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = stringResource(id = label), color = Color.White) },
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.White,
                    backgroundColor = Color.Gray.copy(alpha = 0.2f),
                    focusedIndicatorColor = Color.White
                ),
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
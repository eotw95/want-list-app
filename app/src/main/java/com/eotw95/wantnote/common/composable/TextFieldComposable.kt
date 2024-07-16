package com.eotw95.wantnote.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eotw95.wantnote.common.ext.fieldModifier

@Composable
fun BasicTextField(
    newValue: String,
    onValueChange: (String) -> Unit,
    placeholder: Int
) {
    OutlinedTextField(
        value = newValue,
        onValueChange = onValueChange,
        placeholder = { Text(text = stringResource(id = placeholder)) },
        singleLine = false,
        modifier = Modifier.fieldModifier()
    )
}

@Composable
fun BasicField(
    text: String
) {
    Card(
        modifier = Modifier.fieldModifier(),
        border = BorderStroke(0.dp, Color.Transparent)
    ) {
        Text(text = text, modifier = Modifier.padding(16.dp, 5.dp))
    }
}

@Composable
fun LinkField(
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
        border = BorderStroke(0.dp, Color.Transparent)
    ) {
        RowCenterHaveClickAction(onClick = { if (isExist) onUrlClick(encoderUrl) }) {
            TextField(
                value = linkText,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = stringResource(id = label)) },
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.primary
                )
            )
        }
    }
}

@Composable
fun DescField(
    text: String,
    label: Int,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 0.dp),
        border = BorderStroke(0.dp, Color.Transparent)
    ) {
        ColumnCenterContent {
            TextField(
                value = text,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = stringResource(id = label)) },
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabField(
    text: String,
    label: Int,
) {
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 0.dp),
        border = BorderStroke(0.dp, Color.Transparent)
    ) {
        TextField(
            value = text,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = stringResource(id = label)) },
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { showBottomSheet.value = true }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                }
            }
        )
    }

    if (showBottomSheet.value) {
        BasicBottomSheet(
            state = sheetState,
            hide = { showBottomSheet.value = false },
            onItemClick = {}
        )
    }
}
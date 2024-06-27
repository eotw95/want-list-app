package com.eotw95.wantnote.common.composable

import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun BasicAlertDialog(
    text: Int,
    confirm: Int,
    dismiss: Int,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismissClick() },
        confirmButton = { OutlinedButton(onClick = onConfirmClick){
            Text(text = stringResource(id = confirm)) }
        },
        dismissButton = { OutlinedButton(onClick = onDismissClick){
            Text(text = stringResource(id = dismiss)) }
        },
        text = { Text(text = stringResource(id = text)) },
    )
}
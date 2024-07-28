package com.eotw95.wantnote.common.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.eotw95.wantnote.common.ext.fieldModifier
import com.eotw95.wantnote.room.TabInfo

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BasicTextField(
    newValue: String,
    onValueChange: (String) -> Unit,
    placeholder: Int
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = newValue,
        onValueChange = onValueChange,
        placeholder = { Text(text = stringResource(id = placeholder)) },
        singleLine = false,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = MaterialTheme.colors.onPrimary,
            focusedIndicatorColor = MaterialTheme.colors.onPrimary
        ),
        modifier = Modifier.fieldModifier(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabField(
    tabInfo: TabInfo,
    editedTabInfo: TabInfo,
    label: Int,
    onDeleteClick: (TabInfo) -> Unit,
    onNameChanged: (String) -> Unit,
    onEditFinishClick: (TabInfo, TabInfo) -> Unit
) {
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = tabInfo.name,
        onValueChange = {},
        readOnly = true,
        label = { Text(text = stringResource(id = label)) },
        enabled = false,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = MaterialTheme.colors.onPrimary,
            backgroundColor = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
        ),
        trailingIcon = {
            IconButton(
                onClick = {
                    onNameChanged(tabInfo.name)
                    showBottomSheet.value = true
                }
            ) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
            }
        }
    )

    if (showBottomSheet.value) {
        EditTabBottomSheet(
            tabInfo = tabInfo,
            editedTabInfo = editedTabInfo,
            state = sheetState,
            hide = { showBottomSheet.value = false },
            onEditFinishClick = onEditFinishClick,
            onDeleteClick = onDeleteClick,
            onNameChanged = onNameChanged
        )
    }
}
package com.eotw95.wantnote.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eotw95.wantnote.R
import com.eotw95.wantnote.TAB_ALL
import com.eotw95.wantnote.room.TabInfo
import com.eotw95.wantnote.room.WantItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicBottomSheet(state: SheetState, hide: () -> Unit, onItemClick: (String) -> Unit) {
    ModalBottomSheet(
        onDismissRequest = hide,
        sheetState = state,
        containerColor = MaterialTheme.colors.primary,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "sample",
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clickable { onItemClick("sample") }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTabBottomSheet(
    tab: TabInfo,
    state: SheetState,
    hide: () -> Unit,
    onAddClick: (TabInfo) -> Unit,
    onInfoChanged: (String) -> Unit,
    open: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = hide,
        sheetState = state,
        containerColor = MaterialTheme.colors.primary,
        windowInsets = WindowInsets(bottom = 100.dp)
    ) {
        BasicColumn {
            BasicTextField(
                newValue = tab.name,
                onValueChange = onInfoChanged,
                placeholder = R.string.tab_placeholder
            )

            BasicDivider()

            BasicButton(
                text = R.string.add_tab,
                onClick = {
                    onAddClick(tab)
                    hide()
                    open()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLinkBottomSheet(
    editedItem: WantItem,
    state: SheetState,
    hide: () -> Unit,
    onAddClick: () -> Unit,
    onInfoChanged: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = hide,
        sheetState = state,
        containerColor = MaterialTheme.colors.primary,
        windowInsets = WindowInsets(bottom = 100.dp)
    ) {
        BasicColumn {
            BasicTextField(
                newValue = editedItem.link,
                onValueChange = onInfoChanged,
                placeholder = R.string.link_placeholder
            )

            BasicDivider()

            BasicButton(
                text = R.string.text_add_link,
                onClick = {
                    onAddClick()
                    hide()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCategoryBottomSheet(
    state: SheetState,
    tabs: List<String>,
    hide: () -> Unit,
    onItemClick: (String) -> Unit,
    showTabText: () -> Unit,
    onTabTextChanged: (String) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = hide,
        sheetState = state,
        containerColor = MaterialTheme.colors.primary,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            tabs.forEach { item ->
                if (item != TAB_ALL) {
                    SelectTabButton(text = item) {
                        onItemClick(item)
                        onTabTextChanged(item)
                        showTabText()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTabBottomSheet(
    tabInfo: TabInfo,
    editedTabInfo: TabInfo,
    state: SheetState,
    hide: () -> Unit,
    onEditFinishClick: (TabInfo, TabInfo) -> Unit,
    onDeleteClick: (TabInfo) -> Unit,
    onNameChanged: (String) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = hide,
        sheetState = state,
        containerColor = MaterialTheme.colors.primary,
        windowInsets = WindowInsets(bottom = 100.dp)
    ) {
        BasicColumn {
            BasicTextField(
                newValue = editedTabInfo.name,
                onValueChange = onNameChanged,
                placeholder = R.string.tab_placeholder
            )

            BasicDivider()

            BasicButton(
                text = R.string.edit_tab,
                onClick = {
                    onEditFinishClick(tabInfo, editedTabInfo)
                    hide()
                }
            )

            BasicDivider()

            BasicButton(
                text = R.string.delete_tab,
                textColor = MaterialTheme.colors.error,
                buttonBackgroundColor = Color.Transparent,
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.error),
                onClick = { openDialog = true }
            )
        }
    }

    if (openDialog) {
        BasicAlertDialog(
            text = R.string.text_delete_dialog,
            confirm = R.string.yes,
            dismiss = R.string.no,
            onConfirmClick = {
                onDeleteClick(tabInfo)
                openDialog = false
            },
            onDismissClick = { openDialog = false }
        )
    }
}
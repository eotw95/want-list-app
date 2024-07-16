package com.eotw95.wantnote.common.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eotw95.wantnote.R
import com.eotw95.wantnote.room.TabInfo

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
    info: TabInfo,
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
    ) {
        BasicColumn {
            BasicTextField(
                newValue = info.name,
                onValueChange = onInfoChanged,
                placeholder = R.string.tab_placeholder
            )
            BasicButton(
                text = R.string.add_tab,
                onClick = {
                    onAddClick(info)
                    hide()
                    open()
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
                SelectTabButton(text = item) {
                    onItemClick(item)
                    onTabTextChanged(item)
                    showTabText()
                }
            }
        }
    }
}
package com.eotw95.wantnote.screen.tabInfo

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eotw95.wantnote.common.composable.AddTabBottomSheet
import com.eotw95.wantnote.common.composable.BasicColumn
import com.eotw95.wantnote.common.composable.BasicToolBar
import com.eotw95.wantnote.common.composable.VerticalReorderList
import com.eotw95.wantnote.room.TabInfo

@Composable
fun TabInfoScreen(
    popUp: () -> Unit,
    open: () -> Unit,
    viewModel: TabInfoViewModel
) {
    val tabInfos by viewModel.tabInfos.collectAsState(initial = emptyList())
    var addedTabInfo by viewModel.addedTabInfo
    var editedTabInfo by viewModel.editedTabInfo

    TabInfoScreenContent(
        addedTab = addedTabInfo,
        editedTab = editedTabInfo,
        infos = tabInfos.sortedBy { it.order },
        onAddClick = viewModel::onAddClick,
        onDeleteClick = viewModel::onDeleteClick,
        onNameChangedOfAddedTab = viewModel::onNameChangedOfAddedTab,
        onNameChangedOfEditedTab = viewModel::onNameChangedOfEditedTab,
        onReorderTabs = viewModel::onReorderTabs,
        onEditFinishClick = viewModel::onEditFinishClick,
        open = open,
        popUp = popUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabInfoScreenContent(
    addedTab: TabInfo,
    editedTab: TabInfo,
    infos: List<TabInfo>,
    onAddClick: (TabInfo) -> Unit,
    onDeleteClick: (TabInfo) -> Unit,
    onNameChangedOfAddedTab: (String) -> Unit,
    onNameChangedOfEditedTab: (String) -> Unit,
    onReorderTabs: (List<TabInfo>) -> Unit,
    onEditFinishClick: (TabInfo, TabInfo) -> Unit,
    open: () -> Unit,
    popUp: () -> Unit
) {
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    BasicColumn {
        BasicToolBar(navIcon = Icons.Filled.KeyboardArrowLeft, navAction = popUp) {
            IconButton(onClick = { showBottomSheet = true }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(25.dp), tint = MaterialTheme.colors.onPrimary)
            }
        }
        VerticalReorderList(
            tab = editedTab,
            items = infos.distinct(),
            onReorder = onReorderTabs,
            onDeleteClick = onDeleteClick,
            onNameChanged = onNameChangedOfEditedTab,
            onEditFinishClick = onEditFinishClick
        )
    }

    if (showBottomSheet) {
        AddTabBottomSheet(
            tab = addedTab,
            state = sheetState,
            hide = { showBottomSheet = false },
            onAddClick = onAddClick,
            onInfoChanged = onNameChangedOfAddedTab,
            open = open
        )
    }
}
package com.eotw95.wantnote.screen.tabInfo

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val tabInfo by viewModel.tabInfo

    TabInfoScreenContent(
        info = tabInfo,
        infos = tabInfos.sortedBy { it.order },
        onAddClick = viewModel::onAddClick,
        onInfoChanged = viewModel::onInfoChanged,
        onReorderTabs = viewModel::onReorderTabs,
        open = open,
        popUp = popUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabInfoScreenContent(
    info: TabInfo,
    infos: List<TabInfo>,
    onAddClick: (TabInfo) -> Unit,
    onInfoChanged: (String) -> Unit,
    onReorderTabs: (List<TabInfo>) -> Unit,
    open: () -> Unit,
    popUp: () -> Unit
) {
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }

    BasicColumn {
        BasicToolBar(navIcon = Icons.Filled.ArrowBack, navAction = popUp) {
            IconButton(onClick = { showBottomSheet.value = true }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
        VerticalReorderList(
            items = infos.distinct(),
            onReorder = onReorderTabs
        )
    }

    if (showBottomSheet.value) {
        AddTabBottomSheet(
            info = info,
            state = sheetState,
            hide = { showBottomSheet.value = false },
            onAddClick = onAddClick,
            onInfoChanged = onInfoChanged,
            open = open
        )
    }
}
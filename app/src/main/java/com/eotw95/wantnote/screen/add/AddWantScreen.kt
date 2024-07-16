package com.eotw95.wantnote.screen.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.eotw95.wantnote.KEY_EMPTY
import com.eotw95.wantnote.R
import com.eotw95.wantnote.common.composable.BasicButton
import com.eotw95.wantnote.common.composable.BasicColumn
import com.eotw95.wantnote.common.composable.BasicTextField
import com.eotw95.wantnote.common.composable.BasicToolBar
import com.eotw95.wantnote.common.composable.ColumnScrollableCenterContent
import com.eotw95.wantnote.common.composable.SelectCategoryBottomSheet
import com.eotw95.wantnote.room.TabInfo
import com.eotw95.wantnote.room.WantItem
import com.eotw95.wantnote.screen.tabInfo.TabInfoViewModel

@Composable
fun AddWantScreen(
    openAndPopUp: () -> Unit,
    popUp: () -> Unit,
    onAddImageClick: () -> Unit,
    viewModel: AddWantViewModel = hiltViewModel(),
    tabInfoViewModel: TabInfoViewModel
) {
    val item by viewModel.item
    val tabText by viewModel.tabText
    val imagePath by AddWantViewModel.imagePath.observeAsState()
    val tabInfos by tabInfoViewModel.tabInfos.collectAsState(initial = emptyList())

    val context = LocalContext.current
    LaunchedEffect(key1 = KEY_EMPTY) {
        viewModel.onAddImageClick(null, context)
    }

    AddWantScreenContent(
        item = item,
        imagePath = imagePath,
        tabInfos = tabInfos,
        tabText = tabText,
        openAndPopUp = openAndPopUp,
        popUp = popUp,
        onAddItemClick = viewModel::onAddItemClick,
        onAddImageClick = onAddImageClick,
        onLinkChange = viewModel::onLinkChange,
        onDescChange = viewModel::onDescChange,
        onCategoryClick = viewModel::onCategoryClick,
        onTabTextChanged = viewModel::onTabTextChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWantScreenContent(
    item: WantItem,
    imagePath: String?,
    tabInfos: List<TabInfo>,
    tabText: String,
    openAndPopUp: () -> Unit,
    popUp: () -> Unit,
    onAddItemClick: (WantItem) -> Unit,
    onAddImageClick: () -> Unit,
    onLinkChange: (String) -> Unit,
    onDescChange: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onTabTextChanged: (String) -> Unit
){
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }
    val showTabText = remember { mutableStateOf(false) }

    BasicColumn {
        BasicToolBar(navIcon = Icons.Filled.ArrowBack, navAction = popUp) { /* do nothing */ }
        ColumnScrollableCenterContent {
            BasicTextField(
                newValue = item.link,
                onValueChange = onLinkChange,
                placeholder = R.string.link_placeholder
            )
            BasicButton(text = R.string.add_image, onClick = onAddImageClick)
            if (imagePath != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = imagePath),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(150.dp)
                )
            }
            BasicTextField(
                newValue = item.description,
                onValueChange = onDescChange,
                placeholder = R.string.desc_placeholder
            )
            BasicButton(text = R.string.add_tab, onClick = { showBottomSheet.value = true })
            if (showTabText.value) Text(text = tabText)

            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            BasicButton(text = R.string.add_item) {
                onAddItemClick(
                    WantItem(
                        id = 0,
                        link = item.link,
                        description = item.description,
                        imagePath = imagePath ?: "",
                        category = item.category
                    )
                )
                if (!imagePath.isNullOrBlank()) openAndPopUp()
            }

            if (showBottomSheet.value) {
                SelectCategoryBottomSheet(
                    state = sheetState,
                    tabs = tabInfos.map { it.name },
                    hide = { showBottomSheet.value = false },
                    onItemClick = { item ->
                        onCategoryClick(item)
                        showBottomSheet.value = false
                    },
                    showTabText = { showTabText.value = true },
                    onTabTextChanged = onTabTextChanged
                )
            }
        }
    }
}
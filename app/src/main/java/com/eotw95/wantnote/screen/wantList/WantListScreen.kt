package com.eotw95.wantnote.screen.wantList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eotw95.wantnote.R
import com.eotw95.wantnote.common.composable.BasicColumn
import com.eotw95.wantnote.common.composable.BasicGridImage
import com.eotw95.wantnote.common.composable.BasicHorizontalIndicator
import com.eotw95.wantnote.common.composable.BasicHorizontalPager
import com.eotw95.wantnote.common.composable.ColumnCenterContent
import com.eotw95.wantnote.common.composable.TopToolBar
import com.eotw95.wantnote.room.TabInfo
import com.eotw95.wantnote.room.WantItem
import com.eotw95.wantnote.screen.tabInfo.TabInfoViewModel

@Composable
fun WantListScreen(
    onCellClick: (String) -> Unit,
    onTabClick: () -> Unit,
    onSettingClick: () -> Unit,
    onActionButtonClick: () -> Unit,
    viewModel: WantListViewModel = hiltViewModel(),
    tabInfoViewModel: TabInfoViewModel
) {
    val items by viewModel.items.collectAsState(initial = emptyList())
    val tabs by tabInfoViewModel.tabInfos.collectAsState(initial = emptyList())

    WantListScreenContent(
        items = items,
        tabs = tabs.sortedBy { it.order },
        onCellClick = onCellClick,
        onTabClick = onTabClick,
        onSettingClick = onSettingClick,
        onActionButtonClick = onActionButtonClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WantListScreenContent(
    items: List<WantItem>,
    tabs: List<TabInfo>,
    onCellClick: (String) -> Unit,
    onTabClick: () -> Unit,
    onSettingClick: () -> Unit,
    onActionButtonClick: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onActionButtonClick, shape = RoundedCornerShape(15.dp)) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            BasicColumn {
                TopToolBar(title = R.string.title_tool_bar) {
                    IconButton(onClick = onTabClick) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                    IconButton(onClick = onSettingClick) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
                    }
                }

                val tabNames = tabs.map { it.name }
                BasicHorizontalIndicator(state = pagerState, indicators = tabNames)
                BasicHorizontalPager(pagerState = pagerState) {
                    if (items.isEmpty()) {
                        ColumnCenterContent {
                            Text(
                                text = stringResource(id = R.string.add_what_you_want),
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    } else {
                        val sortedItems = mutableListOf<WantItem>()
                        items.forEach { item ->
                            if (pagerState.currentPage == 0) {
                                sortedItems.add(item)
                            } else {
                                if (item.category == tabNames[pagerState.currentPage]) {
                                    sortedItems.add(item)
                                }
                            }
                        }
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 125.dp),
                            modifier = Modifier.fillMaxHeight(),
                            content = {
                                if (!sortedItems.isNullOrEmpty()) {
                                    items(sortedItems) { item ->
                                        GridItem(
                                            item = item,
                                            openItemDetail = onCellClick
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GridItem(
    item: WantItem,
    openItemDetail: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .clickable { openItemDetail(item.id.toString()) }
            .clip(RoundedCornerShape(10.dp))
    ) { BasicGridImage(imagePath = item.imagePath) }
}
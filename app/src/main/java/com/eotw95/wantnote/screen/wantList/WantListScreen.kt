package com.eotw95.wantnote.screen.wantList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eotw95.wantnote.R
import com.eotw95.wantnote.common.composable.BasicColumn
import com.eotw95.wantnote.common.composable.BasicGridImage
import com.eotw95.wantnote.common.composable.ColumnCenterContent
import com.eotw95.wantnote.room.WantItem

@Composable
fun WantListScreen(
    onCellClick: (String) -> Unit,
    viewModel: WantListViewModel = hiltViewModel()
) {
    val items by viewModel.items.collectAsState(initial = emptyList())
    WantListScreenContent(items = items, onCellClick = onCellClick)
}

@Composable
fun WantListScreenContent(
    items: List<WantItem>,
    onCellClick: (String) -> Unit
) {
    if (items.isEmpty()) {
        ColumnCenterContent {
            Text(text = stringResource(id = R.string.add_what_you_want), color = Color.White)
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 125.dp),
            content = {
                if (!items.isNullOrEmpty()) {
                    items(items) { item ->
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

@Composable
fun GridItem(
    item: WantItem,
    openItemDetail: (String) -> Unit
) {
    Surface(modifier = Modifier
        .padding(0.5.dp)
        .clickable { openItemDetail(item.id.toString()) }) {
        BasicGridImage(imagePath = item.imagePath)
    }
}
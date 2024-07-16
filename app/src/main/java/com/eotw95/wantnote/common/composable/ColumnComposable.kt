package com.eotw95.wantnote.common.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.eotw95.wantnote.R
import com.eotw95.wantnote.TAB_ALL
import com.eotw95.wantnote.room.TabInfo
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun BasicColumn(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        content = content
    )
}

@Composable
fun BasicScrollableColumn(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        content = content
    )
}

@Composable
fun ColumnCenterContent(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}

@Composable
fun ColumnScrollableCenterContent(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}

@Composable
fun VerticalReorderList(
    items: List<TabInfo>,
    onReorder: (List<TabInfo>) -> Unit
) {
    if (items.isEmpty()) return
    val data = remember { mutableStateOf(items) }
    // val data = remember(items) { mutableStateOf(items) } では onMove{} で参照してる data.value が最新の値に追従していない
    // LaunchedEffect{} で data.value を更新すると onMove{} 内で最新の data.value を参照できる
    LaunchedEffect(key1 = items) {
        data.value = items
    }

    val state = rememberReorderableLazyListState(
        onMove = { from, to ->
            data.value = data.value.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
        },
        onDragEnd = { _, _ ->
            onReorder(data.value)
        }
    )
    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        items(data.value, { it.id }) { item ->
            if (item.name != TAB_ALL) {
                ReorderableItem(
                    reorderableState = state,
                    key = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) { isDragging ->
                    val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                    Column(
                        modifier = Modifier.shadow(elevation.value)
                    ) {
                        TabField(text = item.name, label = R.string.label_tab)
                    }
                }
            }
        }
    }
}
package com.eotw95.wantnote.screen.preview

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eotw95.wantnote.R
import com.eotw95.wantnote.TAB_ALL
import com.eotw95.wantnote.common.composable.AddLinkBottomSheet
import com.eotw95.wantnote.common.composable.BasicAlertDialog
import com.eotw95.wantnote.common.composable.BasicButton
import com.eotw95.wantnote.common.composable.BasicDivider
import com.eotw95.wantnote.common.composable.BasicImage
import com.eotw95.wantnote.common.composable.BasicScrollableColumn
import com.eotw95.wantnote.common.composable.BasicText
import com.eotw95.wantnote.common.composable.BasicTextField
import com.eotw95.wantnote.common.composable.LinkButton
import com.eotw95.wantnote.common.composable.RowHaveClickAction
import com.eotw95.wantnote.common.composable.TransparentToolBar
import com.eotw95.wantnote.common.ext.fieldModifier
import com.eotw95.wantnote.room.TabInfo
import com.eotw95.wantnote.room.WantItem
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PreviewWantScreen(
    id: Int,
    onUrlClick: (String) -> Unit,
    openAndPopUp: () -> Unit,
    popUp: () -> Unit,
    viewModel: PreviewWantViewModel = hiltViewModel()
) {
    val item by viewModel.item
    val tabs by viewModel.tabs.collectAsState(initial = emptyList())
    var existLink by remember { mutableStateOf(false) }
    if (checkValidLink(link = item.link) != stringResource(id = R.string.no_link)) {
        existLink = true
    }
    var editedItem by viewModel.editedItem

    LaunchedEffect(key1 = id, item) {
        viewModel.fetchItemBy(id)
        editedItem = item
    }
    
    PreviewWantScreenContent(
        item = item,
        editedItem = editedItem,
        tabs = tabs,
        existLink = existLink,
        onDeleteClick = viewModel::onDeleteClick,
        onUrlClick = onUrlClick,
        onDescChange = viewModel::onDescChange,
        onLinkChange = viewModel::onLinkChange,
        onAddLinkFinishClick = viewModel::onAddLinkFinishClick,
        openAndPopUp = openAndPopUp,
        popUp = popUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewWantScreenContent(
    item: WantItem,
    editedItem: WantItem,
    tabs: List<TabInfo>,
    existLink: Boolean,
    onDeleteClick: (Int) -> Unit,
    onUrlClick: (String) -> Unit,
    onDescChange: (String) -> Unit,
    onLinkChange: (String) -> Unit,
    onAddLinkFinishClick: () -> Unit,
    openAndPopUp: () -> Unit,
    popUp: () -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()

    BasicScrollableColumn {
        ImageWithOverlay(
            item = item,
            popUp = popUp,
        )

        BasicText(text = R.string.text_tab)
        LazyRow(modifier = Modifier.fieldModifier()) {
            items(tabs) { tab ->
                if (tab.name != TAB_ALL) {
                    val tabColor = if (item.category == tab.name) Color(0xFFFE9395)
                        else MaterialTheme.colors.onPrimary.copy(alpha = 0.1f)
                    OutlinedButton(
                        border = null,
                        elevation = null,
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = tabColor
                        ),
                        modifier = Modifier.padding(horizontal = 5.dp),
                        onClick = { /*TODO*/ }
                    ) { Text(text = tab.name, fontSize = 10.sp) }
                }
            }
        }

        BasicDivider()

        if (existLink) {
            BasicText(text = R.string.text_link)
            LinkButton(
                encoderUrl = URLEncoder.encode(item.link, StandardCharsets.UTF_8.toString()),
                linkText = checkValidLink(link = item.link),
                onUrlClick = onUrlClick
            )
        } else {
            RowHaveClickAction(onClick = { showBottomSheet = true }) {
                Text(text = stringResource(id = R.string.text_link), fontWeight = FontWeight.Bold, color = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(id = R.string.text_add), fontWeight = FontWeight.Bold, color = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f))
                    Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f))
                }
            }
        }

        BasicDivider()

        BasicText(text = R.string.label_memo)
        BasicTextField(newValue = editedItem.description, onValueChange = onDescChange, placeholder = R.string.label_memo)

        BasicDivider()

        BasicButton(
            text = R.string.delete_item,
            textColor = MaterialTheme.colors.error,
            buttonBackgroundColor = Color.Transparent,
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.error),
            onClick = { openDialog = true }
        )

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        if (openDialog) {
            BasicAlertDialog(
                text = R.string.text_delete_dialog,
                confirm = R.string.yes,
                dismiss = R.string.no,
                onConfirmClick = {
                    openDialog = false
                    onDeleteClick(item.id)
                    openAndPopUp()
                },
                onDismissClick = { openDialog = false }
            )
        }

        if (showBottomSheet) {
            AddLinkBottomSheet(
                editedItem = editedItem,
                state = sheetState,
                hide = { showBottomSheet = false },
                onAddClick = onAddLinkFinishClick,
                onInfoChanged = onLinkChange
            )
        }
    }
}

@Composable
fun ImageWithOverlay(
    item: WantItem,
    popUp: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        BasicImage(imagePath = item.imagePath)
        TransparentToolBar(
            navIcon = Icons.Filled.KeyboardArrowLeft,
            navAction = { popUp() }
        ) { /* do nothing */ }
    }
}

@Composable
private fun checkValidLink(link: String): String {
    return if (link.startsWith("http")) {
        if (link.length > 30) link.substring(0, 30) + "..."
        else link
    } else {
        stringResource(id = R.string.no_link)
    }
}
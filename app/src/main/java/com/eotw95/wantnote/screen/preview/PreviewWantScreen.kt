package com.eotw95.wantnote.screen.preview

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eotw95.wantnote.R
import com.eotw95.wantnote.common.composable.BasicAlertDialog
import com.eotw95.wantnote.common.composable.BasicIcon
import com.eotw95.wantnote.common.composable.BasicImage
import com.eotw95.wantnote.common.composable.BasicScrollableColumn
import com.eotw95.wantnote.common.composable.DescField
import com.eotw95.wantnote.common.composable.LinkField
import com.eotw95.wantnote.common.composable.TransparentToolBar
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
    var existLink by remember { mutableStateOf(false) }
    if (checkValidLink(link = item.link) != stringResource(id = R.string.no_link)) {
        existLink = true
    }

    LaunchedEffect(key1 = id) {
        viewModel.fetchItemBy(id)
    }
    
    PreviewWantScreenContent(
        item = item,
        existLink = existLink,
        onDeleteClick = viewModel::onDeleteClick,
        onUrlClick = onUrlClick,
        openAndPopUp = openAndPopUp,
        popUp = popUp
    )
}

@Composable
fun PreviewWantScreenContent(
    item: WantItem,
    existLink: Boolean,
    onDeleteClick: (Int) -> Unit,
    onUrlClick: (String) -> Unit,
    openAndPopUp: () -> Unit,
    popUp: () -> Unit
) {
    BasicScrollableColumn {
        ImageWithOverlay(
            item = item,
            openAndPopUp = openAndPopUp,
            popUp = popUp,
            onDeleteClick = onDeleteClick
        )
        Text(text = item.category, color = Color.Black, modifier = Modifier.padding(vertical = 10.dp))
        LinkField(
            encoderUrl = URLEncoder.encode(item.link, StandardCharsets.UTF_8.toString()),
            linkText = checkValidLink(link = item.link),
            isExist = existLink,
            onUrlClick = onUrlClick,
            label = R.string.label_link
        )
        if (item.description.isNotBlank()) DescField(text = item.description, label = R.string.label_memo)
    }
}

@Composable
fun ImageWithOverlay(
    item: WantItem,
    openAndPopUp: () -> Unit,
    popUp: () -> Unit,
    onDeleteClick: (Int) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        BasicImage(imagePath = item.imagePath)
        TransparentToolBar(
            navIcon = Icons.Filled.ArrowBack,
            navAction = { popUp() }
        ) {
            var openDialog by remember { mutableStateOf(false) }
            BasicIcon(icon = Icons.Filled.Delete) { openDialog = true }
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
        }
    }
}

@Composable
private fun checkValidLink(link: String): String {
    return if (link.startsWith("http")) {
        if (link.length > 30) link.substring(0, 40) + "..."
        else link
    } else {
        stringResource(id = R.string.no_link)
    }
}
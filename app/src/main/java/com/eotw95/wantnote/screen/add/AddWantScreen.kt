package com.eotw95.wantnote.screen.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.eotw95.wantnote.R
import com.eotw95.wantnote.common.composable.BasicButton
import com.eotw95.wantnote.common.composable.BasicTextField
import com.eotw95.wantnote.common.composable.ColumnCenterContent
import com.eotw95.wantnote.room.WantItem

@Composable
fun AddWantScreen(
    openAndPopUp: () -> Unit,
    onAddImageClick: () -> Unit,
    viewModel: AddWantViewModel = hiltViewModel()
) {
    val item by viewModel.item
    val imagePath by AddWantViewModel.imagePath.observeAsState()

    AddWantScreenContent(
        item = item,
        imagePath = imagePath,
        openAndPopUp = openAndPopUp,
        onAddItemClick = viewModel::onAddItemClick,
        onAddImageClick = onAddImageClick,
        onLinkChange = viewModel::onLinkChange,
        onDescChange = viewModel::onDescChange
    )
}

@Composable
fun AddWantScreenContent(
    item: WantItem,
    imagePath: String?,
    openAndPopUp: () -> Unit,
    onAddItemClick: (WantItem) -> Unit,
    onAddImageClick: () -> Unit,
    onLinkChange: (String) -> Unit,
    onDescChange: (String) -> Unit
){
    ColumnCenterContent {
        BasicTextField(
            newValue = item.link,
            onValueChange = onLinkChange,
            text = R.string.link_placeholder
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
            text = R.string.desc_placeholder
        )
        BasicButton(text = R.string.add_item) {
            onAddItemClick(
                WantItem(
                    id = 0,
                    link = item.link,
                    description = item.description,
                    imagePath = imagePath ?: ""
                )
            )
            // Todo: ロジックは ViewModel に実装しないとダメ
            if (!imagePath.isNullOrBlank()) openAndPopUp()
        }
    }
}
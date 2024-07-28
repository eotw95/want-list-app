package com.eotw95.wantnote.screen.preview

import androidx.compose.runtime.mutableStateOf
import com.eotw95.wantnote.WantRepository
import com.eotw95.wantnote.room.WantItem
import com.eotw95.wantnote.screen.WantNoteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreviewWantViewModel @Inject constructor(
    private val repository: WantRepository
): WantNoteViewModel() {
    val item = mutableStateOf(WantItem(0, "", "", "", ""))
    val tabs = repository.getTabInfos()
    var editedItem = mutableStateOf(WantItem(0, "", "", "", ""))

    fun onDescChange(newValue: String) {
        launchCatching {
            editedItem.value = editedItem.value.copy(description = newValue)
            val updatedItem = item.value.copy(description = editedItem.value.description)
            repository.update(updatedItem)
        }
    }

    fun onLinkChange(newValue: String) { editedItem.value = editedItem.value.copy(link = newValue) }

    fun onAddLinkFinishClick() {
        launchCatching {
            item.value = item.value.copy(link = editedItem.value.link)
            repository.update(item.value)
            resetEditedItemInfo()
        }
    }

    fun onDeleteClick(id: Int) {
        launchCatching {
            val item = repository.getItemById(id)
            repository.delete(item)
        }
    }

    fun fetchItemBy(id: Int) = launchCatching { item.value = repository.getItemById(id) }

    private fun resetEditedItemInfo() { editedItem.value = editedItem.value.copy(link = "") }
}
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
    val item = mutableStateOf(WantItem(0, "", "", ""))
    fun onDeleteClick(id: Int) {
        launchCatching {
            val item = repository.getItemById(id)
            repository.delete(item)
        }
    }

    fun fetchItemBy(id: Int) = launchCatching { item.value = repository.getItemById(id) }
}
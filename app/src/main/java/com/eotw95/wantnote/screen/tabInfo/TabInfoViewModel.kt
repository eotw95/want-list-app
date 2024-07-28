package com.eotw95.wantnote.screen.tabInfo

import androidx.compose.runtime.mutableStateOf
import com.eotw95.wantnote.TAB_ALL
import com.eotw95.wantnote.TAB_SAMPLE
import com.eotw95.wantnote.WantRepository
import com.eotw95.wantnote.room.TabInfo
import com.eotw95.wantnote.screen.WantNoteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class TabInfoViewModel @Inject constructor(private val repository: WantRepository): WantNoteViewModel() {
    val tabInfos: Flow<List<TabInfo>> = repository.getTabInfos()
    var addedTabInfo = mutableStateOf(TabInfo(0, "", 0))
    var editedTabInfo = mutableStateOf(TabInfo(0, "", 0))

    fun onNameChangedOfAddedTab(newValue: String) { addedTabInfo.value = addedTabInfo.value.copy(name = newValue) }

    fun onNameChangedOfEditedTab(newValue: String) { editedTabInfo.value = editedTabInfo.value.copy(name = newValue) }

    fun onEditFinishClick(current: TabInfo, new: TabInfo) {
        launchCatching {
            repository.updateTabInfo(current.copy(name = new.name))
            val currentItems = repository.getAll().first()
            currentItems.forEach { item ->
                if (item.category == current.name) {
                    repository.update(item.copy(category = new.name))
                }
            }
        }
    }

    fun onAddClick(item: TabInfo) {
        launchCatching {
            var maxOrder = 0
            repository.getTabInfos().first().forEach {
               if (maxOrder < it.order) maxOrder = it.order
            }
            repository.insertTabInfo(item.copy(order = maxOrder + 1))
            resetInfo()
        }
    }

    fun onDeleteClick(tab: TabInfo) {
        launchCatching {
            repository.deleteTabInfo(tab)
            val currentItems = repository.getAll().first()
            currentItems.forEach { item ->
                if (item.category == tab.name) {
                    repository.update(item.copy(category = ""))
                }
            }
        }
    }

    fun setupDefaultTab() {
        launchCatching {
            val current = repository.getTabInfos().first()
            val defaultTab = current.find { it.name == TAB_ALL || it.name == TAB_SAMPLE }
            if (defaultTab == null) {
                repository.insertTabInfo(TabInfo(0, TAB_ALL, 0))
                repository.insertTabInfo(TabInfo(0, TAB_SAMPLE, 1))
            }
        }
    }

    fun onReorderTabs(new: List<TabInfo>) {
        launchCatching {
            val current = repository.getTabInfos().first()
            val updatedTabs = new.mapIndexed { index, tab ->
                current.find { it.name == tab.name }?.apply { order = index } ?: TabInfo(0, tab.name, index)
            }
            repository.reorder(updatedTabs)
        }
    }

    private fun resetInfo() { addedTabInfo.value = addedTabInfo.value.copy(name = "") }
}
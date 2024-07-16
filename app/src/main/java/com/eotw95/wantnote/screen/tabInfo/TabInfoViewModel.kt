package com.eotw95.wantnote.screen.tabInfo

import androidx.compose.runtime.mutableStateOf
import com.eotw95.wantnote.R
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
    var tabInfo = mutableStateOf(TabInfo(0, "", 0))

    fun onInfoChanged(newValue: String) { tabInfo.value = tabInfo.value.copy(name = newValue) }

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

    private fun resetInfo() { tabInfo.value = tabInfo.value.copy(name = "") }
}
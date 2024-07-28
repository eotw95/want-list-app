package com.eotw95.wantnote.screen.wantList

import com.eotw95.wantnote.WantRepository
import com.eotw95.wantnote.room.CategorizedItem
import com.eotw95.wantnote.room.WantItem
import com.eotw95.wantnote.screen.WantNoteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class WantListViewModel @Inject constructor(private val repository: WantRepository): WantNoteViewModel() {
    val items = repository.getAll()
    val categorizedItems = repository.getCategorizedItems()
    private val tabs = repository.getTabInfos()

    fun syncItems() {
        launchCatching {
            val tmpItems = mutableListOf<WantItem>()
            items.collect { collectedItems ->
                tabs.first().sortedBy { it.order }.forEach { tab ->
                    collectedItems.forEach { item ->
                        if (item.category == tab.name) {
                            tmpItems.add(item)
                        }
                    }
                    repository.insertCategorizedItem(CategorizedItem(0, tmpItems))
                }
            }
            println("syncItems items=${items.first()}")
            println("syncItems categorizedItems=${categorizedItems.first()}")
        }
    }
}
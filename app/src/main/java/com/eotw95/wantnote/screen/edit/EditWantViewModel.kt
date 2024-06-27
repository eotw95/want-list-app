package com.eotw95.wantnote.screen.edit

import com.eotw95.wantnote.WantRepository
import com.eotw95.wantnote.room.WantItem
import com.eotw95.wantnote.screen.WantNoteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Not necessary at this time.
 */
@HiltViewModel
class EditWantViewModel @Inject constructor(
    private val repository: WantRepository
): WantNoteViewModel() {
    fun onUpdateClick(item: WantItem) {
        launchCatching {
            repository.update(item)
        }
    }
}
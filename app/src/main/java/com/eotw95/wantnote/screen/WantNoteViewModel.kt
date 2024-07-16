package com.eotw95.wantnote.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class WantNoteViewModel(): ViewModel() {
    fun launchCatching(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try { block() } catch (e: Exception) { e.printStackTrace() }
            }
        }
    }
}
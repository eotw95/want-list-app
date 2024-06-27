package com.eotw95.wantnote.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class WantNoteViewModel(): ViewModel() {
    fun launchCatching(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try { block() } catch (e: Exception) { e.printStackTrace() }
        }
    }
}
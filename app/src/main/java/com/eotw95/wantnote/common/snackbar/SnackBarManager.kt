package com.eotw95.wantnote.common.snackbar

import android.content.res.Resources
import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object SnackBarManager {
    private var _messages = MutableStateFlow<SnackBarMessage?>(null)
    val messages: StateFlow<SnackBarMessage?>
        get() = _messages

    fun showMessage(message: SnackBarMessage?) { _messages.value =  message}
}

class SnackBarMessage(@StringRes val messages: Int) {
    companion object {
        fun SnackBarMessage.toMessage(resources: Resources): String {
            return resources.getString(messages)
        }
    }
}
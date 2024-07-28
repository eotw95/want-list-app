package com.eotw95.wantnote.screen.Settings

import androidx.lifecycle.ViewModel
import com.eotw95.wantnote.WantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor (
    private val repository: WantRepository
): ViewModel() {
    fun openWebView(url: String, open: (String) -> Unit) {
        val encodedUrl = URLEncoder.encode(url, "UTF-8")
        open(encodedUrl)
    }
}
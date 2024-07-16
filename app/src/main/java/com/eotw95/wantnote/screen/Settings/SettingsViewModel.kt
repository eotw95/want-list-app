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
    companion object {
        // Todo: fix tmp Url
        private const val URL_PRIVACY_POLICY = "https://developer.android.com/build/configure-app-module?hl=ja#set-namespace"
    }

    fun onClickPrivacyPolicy(open: (String) -> Unit) {
        val encodedUrl = URLEncoder.encode(URL_PRIVACY_POLICY, "UTF-8")
        open(encodedUrl)
    }
}
package com.eotw95.wantnote.screen.web

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebWant(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            webViewClient = WebViewClient()
            settings.apply {
                // Permission access mixed contents HTTP and HTTPS
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                // JavaScript enable
                javaScriptEnabled = true
            }
            loadUrl(url)
        }
    }
    )
}
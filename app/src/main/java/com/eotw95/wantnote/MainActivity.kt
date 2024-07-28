package com.eotw95.wantnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.eotw95.wantnote.common.util.PreferencesUtil
import com.eotw95.wantnote.screen.add.AddWantViewModel
import com.eotw95.wantnote.ui.theme.WantNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: AddWantViewModel by viewModels()
        val launcher =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { tmpUri ->
            tmpUri?.let {
                viewModel.onAddImageClick(it, applicationContext)
            }
        }
        val isFirst = isFirstLaunchApp()

        // Todo: enableEdgeToEdge() の影響で、Android端末で画面下部にシステムバーがあるタイプだとボタンと被ってしまう
        enableEdgeToEdge()
        setContent {
            WantNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WantNoteApp(
                        openMedia = {
                            launcher.launch(PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        isFirst = isFirst
                    )
                }
            }
        }
    }

    private fun isFirstLaunchApp(): Boolean {
        val sp = getSharedPreferences(PreferencesUtil.SETTING_FIRST_LAUNCH, MODE_PRIVATE)
        if (!sp.getBoolean(PreferencesUtil.IS_FIRST, true)) {
            return false
        } else {
            sp.edit().putBoolean(PreferencesUtil.IS_FIRST, false).apply()
            return true
        }
    }
}
package com.eotw95.wantnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
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

        setContent {
            WantNoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WantNoteApp(
                        openMedia = {
                            launcher.launch(PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )
                }
            }
        }
    }
}
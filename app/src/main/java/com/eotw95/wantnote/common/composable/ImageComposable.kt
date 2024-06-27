package com.eotw95.wantnote.common.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun BasicImage(
    imagePath: String,
    height: Dp = 350.dp
) {
    Image(
        painter = rememberAsyncImagePainter(model = File(imagePath)),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth().height(height),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun BasicGridImage(
    imagePath: String
) {
    Image(
        painter = rememberAsyncImagePainter(model = File(imagePath)),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(150.dp)
    )
}
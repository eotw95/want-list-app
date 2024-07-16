package com.eotw95.wantnote.screen.add

import android.content.ContentResolver
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eotw95.wantnote.R
import com.eotw95.wantnote.WantRepository
import com.eotw95.wantnote.common.snackbar.SnackBarManager
import com.eotw95.wantnote.common.snackbar.SnackBarMessage
import com.eotw95.wantnote.room.WantItem
import com.eotw95.wantnote.screen.WantNoteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddWantViewModel @Inject constructor(
    private val repository: WantRepository
): WantNoteViewModel() {

    companion object {
        private var _imagePath = MutableLiveData<String>()
        val imagePath: LiveData<String> = _imagePath
    }

    val item = mutableStateOf(WantItem(0, "", "", "", ""))
    val tabText = mutableStateOf("")

    fun onLinkChange(newValue: String) { item.value = item.value.copy(link = newValue) }

    fun onDescChange(newValue: String) { item.value = item.value.copy(description = newValue) }

    fun onCategoryClick(newValue: String) { item.value = item.value.copy(category = newValue) }

    fun onAddImageClick(image: Uri?, context: Context) {
        uriToBitmap(context.contentResolver, image)?.let { bitmap ->
            saveImageInternalStorage(bitmap, context)?.let {path ->
                _imagePath.postValue(path)
            }
        } ?: _imagePath.postValue(null)
    }

    fun onAddItemClick(item: WantItem) {
        if (item.imagePath.isBlank()) {
            SnackBarManager.showMessage(SnackBarMessage(R.string.image_link_inValid))
            return
        }
        launchCatching {
            repository.insert(item)
        }
    }

    fun onTabTextChanged(newValue: String) { tabText.value = newValue }

    private fun uriToBitmap(contentResolver: ContentResolver, uri: Uri?): Bitmap? {
        return if (uri != null) {
            try {
                val inputStream = contentResolver.openInputStream(uri)
                BitmapFactory.decodeStream(inputStream)
            } catch (e: IOException) {
                e.stackTraceToString()
                null
            }
        } else {
            null
        }
    }

    private fun cropSquare(image: Bitmap): Bitmap {
        if (image.width == image.height) return image
        if (image.width > image.height) {
            val leftOffset = ( image.width - image.height ) / 2
            return Bitmap.createBitmap(
                image,
                leftOffset,
                0,
                image.height,
                image.height
            )
        }

        val topOffset = ( image.height - image.width ) / 2
        return Bitmap.createBitmap(
            image,
            0,
            topOffset,
            image.width,
            image.width,
        )
    }

    private fun saveImageInternalStorage(image: Bitmap, context: Context): String? {
        var path: String? = null

        val dir = ContextWrapper(context)
            .getDir("image", Context.MODE_PRIVATE)
        val file = File(dir, createUniqueName())

        try {
            val outputStream = FileOutputStream(file)
            val crop = cropSquare(image)
            crop.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            path = file.absolutePath
        } catch (e: IOException) {
            e.stackTraceToString()
        }

        return path
    }

    private fun createUniqueName(): String {
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.JAPANESE)
        val now = Date()
        val strDate = dateFormat.format(now)
        return "image_$strDate.jpg"
    }

}
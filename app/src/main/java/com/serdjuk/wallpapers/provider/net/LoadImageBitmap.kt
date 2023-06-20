package com.serdjuk.wallpapers.provider.net

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.serdjuk.wallpapers.provider.Core
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException

suspend fun loadImageBitmap(url: String?): ImageBitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val response = url?.let { Core.client.response(it) }
            val responseBody = response?.body

            if (response?.isSuccessful == true && responseBody != null) {
                val inputStream = responseBody.byteStream()
                val byteArray = inputStream.readBytes()
                inputStream.close()
                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
            } else {
                null
            }
        } catch (e: IOException) {
            null
        }
    }
}






package com.serdjuk.wallpapers.provider.storage

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

suspend fun saveImageToGallery(context: Context, imageBitmap: Bitmap, imageName: String?) =
    withContext(Dispatchers.IO) {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "${imageName}_$timeStamp.jpg"
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val resolver = context.contentResolver
                val collectionUri =
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                val imageUri = resolver.insert(collectionUri, contentValues)

                imageUri?.let { uri ->
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }
                }
            } else {
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val imageFile = File(imagesDir, imageFileName)

                FileOutputStream(imageFile).use { outputStream ->
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                }

                // Обновление медиафайлов в галерее
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(imageFile.absolutePath),
                    null,
                    null
                )
            }
            "Image saved successfully"
        } catch (e: Exception) {
            e.printStackTrace()
            "Failed to save image"
        }
    }
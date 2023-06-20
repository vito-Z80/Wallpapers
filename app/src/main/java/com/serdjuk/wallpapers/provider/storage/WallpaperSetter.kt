package com.serdjuk.wallpapers.provider.storage

import android.app.WallpaperManager
import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.serdjuk.wallpapers.provider.Core
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

suspend fun wallpaperSetter(imageBitmap: ImageBitmap?, context: Context) = Core.coroutine.async(Dispatchers.IO) {
    try {
        val wallpaperManager = WallpaperManager.getInstance(context)
        wallpaperManager.setBitmap(imageBitmap?.asAndroidBitmap())
        "The wallpaper has been set."
    } catch (e: Exception) {
        "Something went wrong."
    }
}.await()


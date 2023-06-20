package com.serdjuk.wallpapers.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SettingsModel(
    val imageList: ImageListSettings = ImageListSettings(),
    val categoryList: CategoryListSettings = CategoryListSettings(),
    val isDarkTheme: MutableState<Boolean> = mutableStateOf(false),
) {
    data class ImageListSettings(
        var imageListRows: MutableState<Int> = mutableStateOf(1),
    )

    data class CategoryListSettings(
        var fontSize: Int = 24,
    )
}
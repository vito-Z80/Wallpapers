package com.serdjuk.wallpapers.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class MutableParams {
    val imageListMenuCategoryName: MutableState<String?> = mutableStateOf(null)
    val imageListPopupVisible = mutableStateOf(false)
}
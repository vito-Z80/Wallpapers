package com.serdjuk.wallpapers.model.viewModel


import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.serdjuk.wallpapers.API_SEARCH
import com.serdjuk.wallpapers.API_URL
import com.serdjuk.wallpapers.API_VERSION
import com.serdjuk.wallpapers.model.ImageModel
import com.serdjuk.wallpapers.model.JsonImageListModel
import com.serdjuk.wallpapers.provider.Core
import com.serdjuk.wallpapers.provider.net.Client
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ImageListViewModel(
    private val clientService: Client,
    private val category: String,
) :
    ViewModel() {
    fun fill(
        snapshotStateList: SnapshotStateList<ImageModel>, page: Int,
        perPage: Int = 60,
    ) {
        Core.coroutine.launch {
            val url by lazy { "$API_URL$API_VERSION$API_SEARCH$category&page=$page&per_page=$perPage" }
            val r = clientService.response(url).body?.string()
                ?.let { Core.gson.fromJson(it, JsonImageListModel::class.java) }
            flow {
                r?.photos?.forEachIndexed { id, photo ->
                    emit(
                        ImageModel(
                            alt = photo?.alt,
                            id = id + (page-1) * perPage,
                            src = ImageModel.Src(
                                medium = photo?.src?.medium,
                                large2x = photo?.src?.large2x
                            )
                        )
                    )
                }
            }.collect { snapshotStateList.add(it) }
        }
    }
}



package com.serdjuk.wallpapers.model.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serdjuk.wallpapers.API_COLLECTIONS
import com.serdjuk.wallpapers.API_URL
import com.serdjuk.wallpapers.API_VERSION
import com.serdjuk.wallpapers.provider.Core
import com.serdjuk.wallpapers.model.CategoryModel
import com.serdjuk.wallpapers.model.JsonCategoryListModel
import com.serdjuk.wallpapers.provider.net.Client
import kotlinx.coroutines.launch


class CategoryListViewModel(private val clientService: Client) : ViewModel() {

    private val url = "$API_URL$API_VERSION$API_COLLECTIONS?page=1&per_page=60"
    private val _categories: MutableLiveData<List<CategoryModel>> = MutableLiveData()
    val categories: LiveData<List<CategoryModel>> get() = _categories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            val r = clientService.response(url).body?.string()
                ?.let { Core.gson.fromJson(it, JsonCategoryListModel::class.java) }
            _categories.value = r?.collections?.map {
                val name = it?.title ?: ""
                val id = it?.id ?: ""
                CategoryModel(name = name, id = id)
            }?.sortedBy { it.name }
        }
    }
}


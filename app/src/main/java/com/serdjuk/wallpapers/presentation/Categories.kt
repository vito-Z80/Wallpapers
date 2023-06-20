package com.serdjuk.wallpapers.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import com.serdjuk.wallpapers.firstUpper
import com.serdjuk.wallpapers.model.Navigator
import com.serdjuk.wallpapers.model.viewModel.CategoryListViewModel
import com.serdjuk.wallpapers.provider.Core
import com.serdjuk.wallpapers.provider.Screen
import kotlinx.coroutines.launch


class Categories : Navigator {

    private val view = CategoryListViewModel(Core.client)
    private var lls = LazyListState(0, 0)


    @Composable
    override fun Show() {
        Core.mutable.imageListPopupVisible.value = false
        val categories by view.categories.observeAsState()
        categories?.let { c ->
            LazyColumn(state = lls) {
                items(items = c, key = { k -> k.id }) { category ->
                    TextButton(modifier = Modifier.fillMaxWidth(), onClick = {
                        view.viewModelScope.launch {
                            Core.mutable.imageListMenuCategoryName.value = category.name
                            Screen.nextScreen(ImageList())
                        }
                    }) {
                        Text(
                            text = category.name.firstUpper(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }
        }
    }
}


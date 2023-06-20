package com.serdjuk.wallpapers.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.serdjuk.wallpapers.model.ImageModel
import com.serdjuk.wallpapers.model.Navigator
import com.serdjuk.wallpapers.model.viewModel.ImageListViewModel
import com.serdjuk.wallpapers.provider.Core


class ImageList : Navigator {
    private var imagePlaceSize = mutableStateOf(DpSize(0.dp, 0.dp))
    private val gridState = mutableStateOf(LazyGridState(0, 0))
    private var page = 1
    private val perPage = 50
    val view =
        ImageListViewModel(Core.client, Core.mutable.imageListMenuCategoryName.value ?: "")
    private val imageList: SnapshotStateList<ImageModel> =
        mutableStateListOf<ImageModel>().also { view.fill(it, page, perPage) }

    // это какие-то цыганские фокусы... Не помню как дошел до этого, но оно как то работает с разными разрешениями экрана.
    private val imagePlaceSizeDivider = 5f

    @Composable
    override fun Show() {
        Core.mutable.imageListPopupVisible.value = true

        LaunchedEffect(gridState.value.firstVisibleItemIndex) {
            if (gridState.value.firstVisibleItemIndex >= page * perPage - (perPage / 2)) {
                view.fill(snapshotStateList = imageList, page = ++page, perPage = perPage)
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                imagePlaceSize.value =
                    DpSize(
                        it.size.width.dp / imagePlaceSizeDivider,
                        it.size.height.dp / imagePlaceSizeDivider
                    )
            })

        LazyVerticalGrid(
            columns = GridCells.Fixed(Core.preference.getSettings(LocalContext.current).imageList.imageListRows.value),
            state = gridState.value,
        ) {


            items(imageList) { im ->
                AsyncImage(
                    model = im.src?.medium,
                    contentDescription = im.alt,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(imagePlaceSize.value)
                        .clip(RoundedCornerShape(15f))
                        .background(MaterialTheme.colorScheme.primary)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSecondary,
                            RoundedCornerShape(15f)
                        )
                        .clickable { Core.screen.nextScreen(SingleImage(im)) },
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
//                Text(   //  TODO debug (remove)
//                    text = im.id.toString(),
//                    style = MaterialTheme.typography.labelSmall,
//                    color = MaterialTheme.colorScheme.onPrimary,
//                    modifier = Modifier
//                        .fillMaxWidth(0.1f)
//                        .background(
//                            color = MaterialTheme.colorScheme.secondary,
//                            shape = CircleShape
//                        )
//                )
            }
        }
    }
}
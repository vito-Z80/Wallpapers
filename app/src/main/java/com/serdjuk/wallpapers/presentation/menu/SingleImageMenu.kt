package com.serdjuk.wallpapers.presentation.menu

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.serdjuk.wallpapers.provider.net.loadImageBitmap
import com.serdjuk.wallpapers.provider.storage.saveImageToGallery
import com.serdjuk.wallpapers.provider.storage.wallpaperSetter

private val menuShape = AbsoluteRoundedCornerShape(
    topLeftPercent = 10,
    topRightPercent = 0,
    bottomLeftPercent = 10,
    bottomRightPercent = 0
)

@Composable
fun SingleImageMenu(menuVisible: MutableState<Boolean>, imageUrl: String?,imageName:String?) {

    var setWallpaper by remember { mutableStateOf(false) }
    var saveWallpaper by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(setWallpaper) {
        if (setWallpaper) {
            val response = wallpaperSetter(
                imageBitmap = loadImageBitmap(imageUrl),
                context = context
            )
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(saveWallpaper) {
        if (saveWallpaper) {
            loadImageBitmap(url = imageUrl)?.asAndroidBitmap()?.let {
                val response = saveImageToGallery(context, imageBitmap = it, imageName = imageName)
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
            }
        }
    }


    AnimatedVisibility(
        visible = menuVisible.value,
        enter = slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(durationMillis = 300)
        ) + fadeIn(),
        exit = slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(durationMillis = 300)
        ) + fadeOut(),
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .animateContentSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = menuShape
                    )
                    .border(
                        width = 1.dp,
                        shape = menuShape,
                        color = MaterialTheme.colorScheme.primary
                    )
            ) {
                LazyColumn() {
                    item {
                        MenuItem(text = "Set Wallpaper") {
                            menuVisible.value = false
                            setWallpaper = true
                        }
                    }
                    item {
                        MenuItem(text = "Save to gallery") {
                            saveWallpaper = true
                            menuVisible.value = false
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MenuItem(text: String, click: () -> Unit) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .clip(
                AbsoluteRoundedCornerShape(
                    25, 25, 25, 25
                )
            )
            .padding(16.dp)
            .clickable { click.invoke() }
    )
}
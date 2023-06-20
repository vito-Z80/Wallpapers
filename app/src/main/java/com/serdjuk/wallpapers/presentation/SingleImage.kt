package com.serdjuk.wallpapers.presentation

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.serdjuk.wallpapers.R
import com.serdjuk.wallpapers.model.ImageModel
import com.serdjuk.wallpapers.model.Navigator
import com.serdjuk.wallpapers.presentation.button.AppButton
import com.serdjuk.wallpapers.presentation.menu.SingleImageMenu
import com.serdjuk.wallpapers.provider.Core

class SingleImage(private val photo: ImageModel) : Navigator {

    private val menuVisible = mutableStateOf(false)

    @Composable
    override fun Show() {
        Core.mutable.imageListPopupVisible.value = false
        val scrollV = rememberScrollState(0)
        val scrollH = rememberScrollState(0)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollV)
                .horizontalScroll(scrollH),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = photo.src?.large2x,
                contentDescription = photo.alt,
                contentScale = ContentScale.Crop,
            )
        }
        AppButton(
            resourceId = R.drawable.free_icon_wallpaper_2207659_graphix_dxinerz,
            contentAlignment = Alignment.TopEnd
        ) {
            menuVisible.value = !menuVisible.value
        }
        Like()

        SingleImageMenu(menuVisible, photo.src?.large2x, photo.alt)
    }

}

@Composable
private fun Like() {
    var likeColor by remember { mutableStateOf(Color.White) }

    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {

        Image(
            painter = painterResource(id = R.drawable.pngegg),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .clip(CircleShape)

                .clickable {
                    likeColor = if (likeColor == Color.White) {
                        Color.Red
                    } else {
                        Color.White
                    }
                }
                .padding(24.dp)
                .scale(scale),
            colorFilter = ColorFilter.tint(likeColor)
        )
    }
}
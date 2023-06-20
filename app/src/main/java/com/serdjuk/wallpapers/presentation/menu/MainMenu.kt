package com.serdjuk.wallpapers.presentation.menu

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.serdjuk.wallpapers.LABEL_THEME_DARK
import com.serdjuk.wallpapers.LABEL_THEME_EXIT
import com.serdjuk.wallpapers.LABEL_THEME_LIGHT
import com.serdjuk.wallpapers.LABEL_THEME_OPTIONS
import com.serdjuk.wallpapers.LABEL_THEME_THEME
import com.serdjuk.wallpapers.provider.Core
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

private val shape = AbsoluteRoundedCornerShape(20)

@Composable
fun MainMenu(isVisible: MutableState<Boolean>) {

    val boxAlpha by animateFloatAsState(if (isVisible.value) 1f else 0f)

    AnimatedVisibility(
        visible = isVisible.value, enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { isVisible.value = false },
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = shape
                    )
                    .clip(shape = shape)
                    .clickable { }
                    .alpha(boxAlpha)
                    .animateContentSize()
                    .padding(8.dp)
            ) {
                Column {
                    MenuLabel()
                    Spacer(modifier = Modifier.height(20.dp))
                    ThemeButton()
                    ExitButton()
                }
            }
        }
    }
}

@Composable
private fun MenuLabel() {
    Row(modifier = Modifier.fillMaxWidth(0.5f), horizontalArrangement = Arrangement.End) {
        Text(text = LABEL_THEME_OPTIONS, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun ExitButton() {
    val context = LocalContext.current
    Text(text = LABEL_THEME_EXIT, modifier = Modifier
        .fillMaxWidth(0.5f)
        .clip(shape = shape)
        .clickable { (context as Activity).finish() }
        .padding(4.dp)
        .padding(top = 8.dp),
    style = MaterialTheme.typography.labelSmall)
}

@Composable
private fun ThemeButton() {
    val context = LocalContext.current
    val isDarkTheme = Core.preference.getSettings(context).isDarkTheme
    var text by remember { mutableStateOf(if (isDarkTheme.value) LABEL_THEME_DARK else LABEL_THEME_LIGHT) }

    LaunchedEffect(isDarkTheme.value) {
        text = when (isDarkTheme.value) {
            false -> LABEL_THEME_LIGHT
            true -> LABEL_THEME_DARK
        }
        Core.coroutine.async(Dispatchers.IO) {
            Core.preference.writeSettings(context)

        }.await()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .clip(shape = shape)
            .clickable { isDarkTheme.value = !isDarkTheme.value }
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = LABEL_THEME_THEME,style = MaterialTheme.typography.labelSmall)
        Text(text = text,style = MaterialTheme.typography.labelSmall)
    }
}


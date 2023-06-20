package com.serdjuk.wallpapers.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.serdjuk.wallpapers.provider.Core


private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFAAD8D3), // пастельный голубой
    onPrimary = Color(0xFF000000), // черный
    secondary = Color(0xFFF6C3C3), // пастельный розовый
    onSecondary = Color(0xFF000000), // черный
    background = Color(0xFFFFFFFF), // белый
    surface = Color(0xFFFFFFFF), // белый
    onBackground = Color(0xFF333333), // темно-серый
    onSurface = Color(0xFF555555) // серый
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF768FA5), // темно-пастельный голубой
    onPrimary = Color(0xFFFFFFFF), // белый
    secondary = Color(0xFFB47B7B), // темно-пастельный розовый
    onSecondary = Color(0xFFFFFFFF), // белый
    background = Color(0xFF121212), // темно-серый
    surface = Color(0xFF1E1E1E), // темно-серый
    onBackground = Color(0xFFFFFFFF), // белый
    onSurface = Color(0xFFEEEEEE) // светло-серый

)


@Composable
fun WallpapersTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    darkTheme: Boolean = Core.preference.getSettings(LocalContext.current).isDarkTheme.value,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
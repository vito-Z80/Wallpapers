package com.serdjuk.wallpapers

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.serdjuk.wallpapers.provider.Screen
import com.serdjuk.wallpapers.model.Navigator
import com.serdjuk.wallpapers.presentation.Categories
import com.serdjuk.wallpapers.presentation.button.AppButton
import com.serdjuk.wallpapers.presentation.menu.ImageListPopupMenu
import com.serdjuk.wallpapers.presentation.menu.MainMenu
import com.serdjuk.wallpapers.ui.theme.WallpapersTheme
import kotlinx.coroutines.delay

//  https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html

//  https://developer.android.com/jetpack/compose/performance/bestpractices - Производительность.

//  https://www.techotopia.com/index.php/Generating_a_Signed_Release_APK_File_in_Android_Studio - build release

class MainActivity : ComponentActivity() {
    //    private val imageProcess = ImageProcess(Provider())

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT > 9) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        super.onCreate(savedInstanceState)
        setContent {
            val mainMenuVisible = remember { mutableStateOf(false) }
            WallpapersTheme() {
                Scaffold(
                    topBar = { ImageListPopupMenu() },
                    bottomBar = {},
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.secondary
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .clipToBounds()
                        ) {
                            Crossfade(
                                targetState = Screen.getState().value,
                                animationSpec = tween(500)
                            ) {
                                when (it) {
                                    Screen.State.FIRST_SCREEN -> Screen.firstScreen?.Show()
                                    Screen.State.SECOND_SCREEN -> Screen.secondScreen?.Show()
                                }
                            }
                            AppButton(R.drawable.free_icon_main_menu_3934163_rizki_ahmad_fauzi) {
                                mainMenuVisible.value = !mainMenuVisible.value
                            }

                            MainMenu(isVisible = mainMenuVisible)
                        }
                    }
                    BackHandler() {
                        Screen.backScreen()
                    }
                }
            }
        }
    }
}


class Greets : Navigator {

    @Composable
    override fun Show() {
        LaunchedEffect(Unit) {
            delay(500)
            Screen.nextScreen(Categories())
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Pexels.com", fontSize = 40.sp)
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null
            )
        }
    }

}
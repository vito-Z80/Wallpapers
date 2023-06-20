package com.serdjuk.wallpapers.presentation.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.serdjuk.wallpapers.R
import com.serdjuk.wallpapers.presentation.button.AppButton
import com.serdjuk.wallpapers.provider.Core


@Composable
fun ImageListPopupMenu() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 1.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        AnimatedVisibility(visible = Core.mutable.imageListPopupVisible.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.DarkGray)
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = Core.mutable.imageListMenuCategoryName.value ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                AppButton(
                    resourceId = R.drawable.grid_board_cell_table_icon_187134,
                    contentAlignment = Alignment.TopEnd
                ) {
                    val rowNumbers = Core.preference.getSettings(context).imageList.imageListRows
                    if (++rowNumbers.value > 3) {
                        rowNumbers.value = 1
                    }
                    Core.preference.writeSettings(context)
                }
            }
        }
    }
}



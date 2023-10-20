package gio.ado.bruschapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import gio.ado.bruschapp.ui.theme.PaleGreen
import gio.ado.bruschapp.ui.theme.PaleGreen2
import gio.ado.bruschapp.viewmodels.ViewModel

@Composable
fun BottomNavigation(
    viewModelComune: ViewModel,
    isSendCuteMessage: Boolean = false,
    isImageScreen: Boolean = false,
    isCuteMessageCollection: Boolean = false,
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .offset(y = (screenHeightDp.dp - 50.dp))
            .background(PaleGreen),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable { viewModelComune.navigate("imagesScreen") },
                imageVector = Icons.Default.Favorite,
                contentDescription = "null",
                tint = if (isImageScreen) {
                    Color.White
                } else {
                    PaleGreen2
                }
            )
            Spacer(modifier = Modifier.width(60.dp))
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable { viewModelComune.navigate("homeScreen") },
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = "null",
                tint = if (isSendCuteMessage) {
                    Color.White
                } else {
                    PaleGreen2
                }
            )
            Spacer(modifier = Modifier.width(60.dp))
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable { viewModelComune.navigate("collection") },
                imageVector = Icons.Default.Collections,
                contentDescription = "null",
                tint = if (isCuteMessageCollection) {
                    Color.White
                } else {
                    PaleGreen2
                }
            )
        }

    }
}
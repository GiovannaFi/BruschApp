package gio.ado.bruschapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavHostController
import gio.ado.bruschapp.ui.theme.PaleGreen

@Composable
fun BottomNavigation(
    navController: NavHostController
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
//            .offset(y = (screenHeightDp.dp - 50.dp))
            .background(PaleGreen),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable { navController.navigate("homeScreen") },
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = "null",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(60.dp))
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable { navController.navigate("imagesScreen") },
                imageVector = Icons.Default.Favorite,
                contentDescription = "null",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(60.dp))
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable { navController.navigate("collection") },
                imageVector = Icons.Default.Collections,
                contentDescription = "null",
                tint = Color.White
            )
        }

    }
}
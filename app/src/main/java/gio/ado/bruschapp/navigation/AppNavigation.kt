package gio.ado.bruschapp.navigation

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.rememberImagePainter
import gio.ado.bruschapp.SharedImplementation
import gio.ado.bruschapp.presentation.CuteMessageCollection
import gio.ado.bruschapp.presentation.HomePageScreen
import gio.ado.bruschapp.presentation.ImageScreen
import gio.ado.bruschapp.presentation.SendCuteMessage

@Composable
fun AppNavigation(navController: NavHostController) {
    val context = LocalContext.current
     val sharedPrefs = SharedImplementation(context)


    NavHost(
        navController = navController,
        startDestination = "homeScreen"
    ) {
        composable("homeScreen") {
            if(sharedPrefs.getProfile()?.isEmpty() == true){
                HomePageScreen(navController)
            }else{
                SendCuteMessage(
                    navController
                )
            }
        }
        composable("photoScreen") {
            SendCuteMessage(navController)
        }
        composable("imagesScreen") {
            ImageScreen(navController)
        }
        composable("collection"){
            CuteMessageCollection(navController)
        }
    }
}

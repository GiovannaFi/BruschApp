package gio.ado.bruschapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import gio.ado.bruschapp.SharedImplementation
import gio.ado.bruschapp.presentation.CuteMessageCollection
import gio.ado.bruschapp.presentation.HomePageScreen
import gio.ado.bruschapp.presentation.ImageScreen
import gio.ado.bruschapp.presentation.SendCuteMessage
import gio.ado.bruschapp.viewmodels.ViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPrefs = SharedImplementation(context)
    val viewModel = ViewModel(context, navController)

    NavHost(
        navController = navController,
        startDestination = "homeScreen"
    ) {
        composable("homeScreen") {
            if (sharedPrefs.getProfile()?.isEmpty() == true) {
                HomePageScreen(navController)
            } else {
                SendCuteMessage(
                    viewModel
                )
            }
        }
        composable("photoScreen") {
            SendCuteMessage(viewModel)
        }
        composable("imagesScreen") {
            ImageScreen(viewModel)
        }
        composable("collection") {
            CuteMessageCollection(viewModel)
        }
    }
}

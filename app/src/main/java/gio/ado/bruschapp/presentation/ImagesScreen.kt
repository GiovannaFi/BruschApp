package gio.ado.bruschapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import gio.ado.bruschapp.R
import gio.ado.bruschapp.presentation.components.BottomNavigation
import gio.ado.bruschapp.presentation.components.CustomLinearProgressIndicator
import gio.ado.bruschapp.presentation.components.TopBarExtended
import gio.ado.bruschapp.viewmodels.ViewModel

@Composable
fun ImageScreen(
    viewModelComune : ViewModel)
{
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val bitmap = viewModelComune.bitmapLiveData.observeAsState(null)


    LaunchedEffect(key1 = true) {
        viewModelComune.downloadLastImage(context)
    }

    TopBarExtended(dimensionParam = 70, showProfile = true)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (bitmap.value != null) {
            Image(
                bitmap = bitmap.value!!.asImageBitmap(),
                contentDescription = null, // Inserisci una descrizione appropriata
                modifier = Modifier.size(60.dp)
            )
        }
    }

    BottomNavigation(viewModelComune, isImageScreen = true)

}
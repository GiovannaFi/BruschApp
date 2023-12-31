package gio.ado.bruschapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import gio.ado.bruschapp.R
import gio.ado.bruschapp.presentation.components.BottomNavigation
import gio.ado.bruschapp.presentation.components.CustomLinearProgressIndicator
import gio.ado.bruschapp.presentation.components.TopBarExtended
import gio.ado.bruschapp.ui.theme.PaleGreen
import gio.ado.bruschapp.viewmodels.ViewModel

@Composable
fun ImageScreen(
    viewModelComune : ViewModel)
{
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    val bitmap = viewModelComune.bitmapLiveData.observeAsState(null)
    val description = viewModelComune.descriptionLiveData.observeAsState(null)
    val screenWidthDp = LocalConfiguration.current.screenWidthDp

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
                modifier = Modifier.size(screenWidthDp.dp)
                    .padding(horizontal = 16.dp)
            )
        }
        if(description.value!=null){
            Text(
                color = PaleGreen,
                text = "\"${description.value!!}\"",
                fontWeight = FontWeight.Bold,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }

    BottomNavigation(viewModelComune, isImageScreen = true)

}
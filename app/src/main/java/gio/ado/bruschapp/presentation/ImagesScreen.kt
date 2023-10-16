package gio.ado.bruschapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import gio.ado.bruschapp.viewmodels.ViewModel

@Composable
fun ImageScreen() {
    val context = LocalContext.current
    val viewModelComune = ViewModel(context)
    val isLoading = remember { mutableStateOf(false) }
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val bitmap = viewModelComune.bitmapLiveData.observeAsState(null)


    LaunchedEffect(key1 = true) {
        viewModelComune.downloadLastImage(context)
    }


    if (bitmap.value != null) {
        Image(
            bitmap = bitmap.value!!.asImageBitmap(),
            contentDescription = null, // Inserisci una descrizione appropriata
            modifier = Modifier.fillMaxSize()
        )
    }

    if (isLoading.value) {
        CustomLinearProgressIndicator(modifierCard = Modifier.offset(y = (screenHeightDp.dp - 6.dp)))
    }
}
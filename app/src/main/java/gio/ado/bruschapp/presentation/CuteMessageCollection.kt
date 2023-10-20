package gio.ado.bruschapp.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import gio.ado.bruschapp.SharedImplementation
import gio.ado.bruschapp.presentation.components.BottomNavigation
import gio.ado.bruschapp.presentation.components.TopBarExtended
import gio.ado.bruschapp.viewmodels.ViewModel


@Composable
fun CuteMessageCollection(
    navController: NavHostController
) {
    val context = LocalContext.current
    val viewModelComune = ViewModel(context)
    val sharedImplementation = remember {
        SharedImplementation(context)
    }
    LaunchedEffect(key1 = true) {
        viewModelComune.downloadAllImages(context)
    }
    val bitmap = viewModelComune.allBitmapLiveData.observeAsState(null)

    TopBarExtended(dimensionParam = 70, showProfile = true)
    Column(Modifier.padding(top = 75.dp)) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            itemsIndexed(bitmap.value.orEmpty()) { _, image ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                            },
                        shape = RoundedCornerShape(15.dp),
                        elevation = 8.dp
                    ) {
                        ShowImage(image)
                    }
                }
            }
        }
    }
//    BottomNavigation(navController)
}


@Composable
fun ShowImage(image: Bitmap?) {
    val painter = image?.asImageBitmap()
    Box(
        modifier = Modifier
            .height(170.dp)
            .width(130.dp),
        contentAlignment = Alignment.Center
    ) {
        if (painter != null) {
            Image(
                bitmap = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Image(
                painter = painterResource(id = gio.ado.bruschapp.R.drawable.photo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

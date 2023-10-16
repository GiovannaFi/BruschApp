package gio.ado.bruschapp.presentation

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import gio.ado.bruschapp.R
import gio.ado.bruschapp.StorageUtil
import gio.ado.bruschapp.viewmodels.PermissionViewModel
import gio.ado.bruschapp.viewmodels.ViewModel
import java.io.File
import java.util.Objects


@Composable
fun SendCuteMessage(navController: NavHostController) {
    val context = LocalContext.current
    val viewModelComune = ViewModel(context)
    val pViewModel = viewModel<PermissionViewModel>()
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    var captureImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            captureImageUri = uri
        }

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            perms.keys.forEach { permission ->
                pViewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )

            }
        }
    )

    LaunchedEffect(key1 = true) {
        multiplePermissionResultLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            )
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
                .clickable {
                    cameraLauncher.launch(uri)
                },
            imageVector = Icons.Default.PhotoCamera,
            contentDescription = "Camera icon",
            tint = Color.White,
        )

        if (captureImageUri.path?.isNotEmpty() == true) {
            Image(
                modifier = Modifier.padding(16.dp, 8.dp),
                painter = rememberImagePainter(captureImageUri),
                contentDescription = null
            )
        } else {
            Image(
                modifier = Modifier.padding(16.dp, 8.dp),
                painter = painterResource(id = R.drawable.ic_image),
                contentDescription = null
            )
        }


        Button(modifier = Modifier.background(Color.Red), onClick = {
            StorageUtil.uploadToStorage(captureImageUri, context, "image")
        }) {

        }
        Button(modifier = Modifier.background(Color.Yellow), onClick = {
            navController.navigate("imagesScreen")
        }) {

        }


    }


}

fun Context.createImageFile(): File {
    val imageFileName = "myimg"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}





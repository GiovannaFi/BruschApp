package gio.ado.bruschapp.presentation

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import gio.ado.bruschapp.R
import gio.ado.bruschapp.StorageUtil
import gio.ado.bruschapp.presentation.components.BottomNavigation
import gio.ado.bruschapp.presentation.components.TopBarExtended
import gio.ado.bruschapp.ui.theme.Grey
import gio.ado.bruschapp.ui.theme.PaleGreen
import gio.ado.bruschapp.viewmodels.PermissionViewModel
import gio.ado.bruschapp.viewmodels.ViewModel
import java.io.File
import java.util.Objects


@Composable
fun SendCuteMessage(
    navController: NavHostController
) {
    val context = LocalContext.current
    val viewModelComune = ViewModel(context)
    val screenWidthDp = LocalConfiguration.current.screenWidthDp

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

   TopBarExtended(dimensionParam = 240)
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            color = Grey,
            text = "SEND CUTE MESSAGE",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(5.dp))

        if (captureImageUri.path?.isNotEmpty() == true) {
            Image(
                modifier = Modifier.padding(16.dp, 8.dp),
                painter = rememberImagePainter(captureImageUri),
                contentDescription = null
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.photo),
                modifier = Modifier
                    .size(screenWidthDp.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(horizontal = 30.dp)
                    .clickable {
                        cameraLauncher.launch(uri)
                    },
                contentDescription = "Camera icon"
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row() {
            Icon(
                modifier = Modifier
                    .size(50.dp)
                    .clickable {  },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Camera icon",
                tint = PaleGreen
            )
            Spacer(modifier = Modifier.width(10.dp))

            if(captureImageUri.path?.isNotEmpty() == true){
                Icon(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.White, CircleShape)
                        .border(
                            width = 6.dp, // Larghezza del bordo
                            color = PaleGreen, // Colore del bordo
                            shape = CircleShape
                        )
                        .padding(8.dp)
                        .clickable { StorageUtil.uploadToStorage(captureImageUri, context, "image") },
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Camera icon",
                    tint = PaleGreen
                )
            }else{
                Icon(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.White, CircleShape)
                        .border(
                            width = 6.dp, // Larghezza del bordo
                            color = PaleGreen, // Colore del bordo
                            shape = CircleShape
                        )
                        .padding(8.dp)
                        .clickable { cameraLauncher.launch(uri) },
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Camera icon",
                    tint = PaleGreen
                )
            }

            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                modifier = Modifier
                    .size(50.dp)
                    .clickable { navController.navigate("imagesScreen") },
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Camera icon",
                tint = PaleGreen
            )
        }


//        Button(modifier = Modifier.background(Color.Red), onClick = {
//            StorageUtil.uploadToStorage(captureImageUri, context, "image")
//        }) {
//
//        }
//        Button(modifier = Modifier.background(Color.Yellow), onClick = {
////            navController.navigate("imagesScreen")
//        }) {
//
//        }


    }
    BottomNavigation(navController)

}

fun Context.createImageFile(): File {
    val imageFileName = "myimg"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}





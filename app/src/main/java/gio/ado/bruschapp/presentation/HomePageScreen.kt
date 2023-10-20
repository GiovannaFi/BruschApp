package gio.ado.bruschapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import gio.ado.bruschapp.R
import gio.ado.bruschapp.ui.theme.PaleGreen
import gio.ado.bruschapp.viewmodels.ViewModel

@Composable
fun HomePageScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PaleGreen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val viewModel = ViewModel(context)

        Text(
            text = "SCEGLI IL PROFILO",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(110.dp)
                        .clickable {
                            viewModel.setProfile("bistecca")
                            navController.navigate("photoScreen")
                        },
                    painter = painterResource(id = R.drawable.bist),
                    contentDescription = null
                )
                Text(
                    color = Color.White,
                    text = "BISTECCA",
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(40.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(110.dp)
                        .clickable {
                            viewModel.setProfile("bruschetta")
                            navController.navigate("photoScreen")
                        },
                    painter = painterResource(id = R.drawable.brusc),
                    contentDescription = null
                )
                Text(
                    color = Color.White,
                    text = "BRUSCHETTA",
                    fontSize = 14.sp
                )
            }
        }

    }
}
package gio.ado.bruschapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import gio.ado.bruschapp.R
import gio.ado.bruschapp.SharedImplementation
import gio.ado.bruschapp.ui.theme.Grey
import gio.ado.bruschapp.ui.theme.PaleBrown


@Composable
fun TopBarExtended(
    modifier: Modifier = Modifier,
    dimensionParam: Int,
    showProfile: Boolean? = false,
) {
    val context = LocalContext.current
    val profile = SharedImplementation(context).getProfile()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionParam.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 25.dp,
                    bottomEnd = 25.dp
                )
            )
            .background(PaleBrown)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically

        ) {
            if(showProfile == true) {
                Image(
                    modifier = Modifier
                        .size(36.dp)
                        .offset (x = 10.dp),
                    painter = if (profile == "bruschetta") {
                        painterResource(id = R.drawable.bist)
                    } else {
                        painterResource(id = R.drawable.brusc)
                    },
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.offset(x = 18.dp),
                    text = if (profile == "bruschetta") {
                        "BISTECCA:"
                    } else {
                        "BRUSCHETTA:"
                    },
                    style = TextStyle(
                        color = Grey,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun Prova() {
    TopBarExtended(dimensionParam = 55)
}




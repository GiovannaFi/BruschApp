package gio.ado.bruschapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomLinearProgressIndicator(
    modifierCard: Modifier = Modifier,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifierCard.background(
            color = Color.Transparent
        ),
        shape = RoundedCornerShape(0.dp)
    ) {
        LinearProgressIndicator(
            modifier = modifier
                .height(6.dp)
                .fillMaxWidth(),
            color = Color.Red
        )
    }
}
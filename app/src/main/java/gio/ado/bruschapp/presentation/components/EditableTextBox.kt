package gio.ado.bruschapp.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import gio.ado.bruschapp.ui.theme.Grey
import gio.ado.bruschapp.ui.theme.PaleGreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditableTextBox(viewModel: gio.ado.bruschapp.viewmodels.ViewModel) {
    val text = remember { mutableStateOf("") }
    val textDefault = remember { mutableStateOf(true) }
    val openTextField = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    if (textDefault.value) {
        Text(
            modifier = Modifier.clickable { openTextField.value = false },
            color = Color.LightGray,
            text = "scrivi qui il messaggio carino",
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
        )
    }
    if (openTextField.value) {
        Text(
            modifier = Modifier.clickable { openTextField.value = false },
            color = PaleGreen,
            text = text.value,
            fontWeight = FontWeight.Bold,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
        )
    } else {
        BasicTextField(
            modifier = Modifier
                .width(200.dp)
                .clickable { textDefault.value = false },
            textStyle = TextStyle(
                color = Grey, // Cambia il colore del testo a rosso
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic // Rendi il testo in corsivo
            ),
            value = text.value,
            onValueChange = { newText ->
                text.value = newText
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.setCuteMessage(text.value)
                    openTextField.value = true
                    keyboardController?.hide() // Nascondi la tastiera
                    Log.d("culo", text.value)
                }
            )
        )
    }
    Divider(
        Modifier
            .width(200.dp)
            .offset(y = -2.dp)
    )
}


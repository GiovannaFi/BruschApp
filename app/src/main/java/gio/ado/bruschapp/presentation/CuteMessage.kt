package gio.ado.bruschapp.presentation

import android.graphics.Bitmap

data class CuteMessage(
    var image: Bitmap? = null,
    var message: String? = null
){
    constructor() : this(null) // Costruttore senza argomenti
}
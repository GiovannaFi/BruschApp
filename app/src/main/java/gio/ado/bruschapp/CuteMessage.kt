package gio.ado.bruschapp

import android.graphics.Bitmap

data class CuteMessage(
    var image: Bitmap? = null,
    var message: String? = null
){
    constructor() : this(null) // Costruttore senza argomenti
}
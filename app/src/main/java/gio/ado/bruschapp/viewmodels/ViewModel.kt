package gio.ado.bruschapp.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import gio.ado.bruschapp.SharedImplementation
import gio.ado.bruschapp.StorageUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream

data class CuteMessageState(
    val message: String? = null
)

class ViewModel(context: Context, val navController: NavHostController? = null) : ViewModel() {

    private val _bitmapLiveData = MutableLiveData<Bitmap?>()
    val bitmapLiveData: LiveData<Bitmap?> = _bitmapLiveData

    private val _allBitmapLiveData = MutableLiveData<List<Bitmap>>() // Cambia il tipo di LiveData
    val allBitmapLiveData: LiveData<List<Bitmap>> = _allBitmapLiveData

    private val _descriptionLiveData = MutableLiveData<String>() // Cambia il tipo di LiveData
    val descriptionLiveData: LiveData<String> = _descriptionLiveData

    private val _state = mutableStateOf(CuteMessageState())
    val state: State<CuteMessageState> = _state

    private val sharedImplementation = SharedImplementation(context)
    private val collection = sharedImplementation.getProfile()
//    val personalCollectionSaver = Firebase.firestore.collection(collection.orEmpty())


    fun setProfile(profile: String) {
        sharedImplementation.saveProfile(profile)
    }

//    fun saveCuteMessage(cuteMessage: CuteMessage) = CoroutineScope(Dispatchers.IO).launch {
//        try {
//            personalCollectionSaver.add(cuteMessage).await()
//            withContext(Dispatchers.Main) {
//            }
//
//        } catch (e: Exception) {
//            withContext(Dispatchers.Main) {
//                e.message?.let { Log.e("cazzini in culini", it) }
//            }
//        }
//    }

    fun navigate(destination: String) {
        navController?.navigate(destination)
    }

    fun setCuteMessage(message: String) {
        _state.value = state.value.copy(
            message = message
        )
    }

    fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    fun downloadLastImage(context: Context) = CoroutineScope(Dispatchers.IO).launch {
        val child = if (sharedImplementation.getProfile() == "bistecca") {
            "bruschetta/"
        } else {
            "bistecca/"
        }
        try {
            val maxDownloadSize = 10L * 1024 * 1024
            val imageRefs = StorageUtil.imageRef.child(child).listAll().await()

            val lastImageRef = imageRefs.items.maxByOrNull { it.name }

            if (lastImageRef != null) {
                val bytes = lastImageRef.getBytes(maxDownloadSize).await()

                // Leggi l'orientamento dell'immagine utilizzando ExifInterface
                val exif = ExifInterface(ByteArrayInputStream(bytes))
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )

                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                // Ruota la bitmap in base all'orientamento
                val rotatedBitmap = when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bmp, 90f)
                    ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bmp, 180f)
                    ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bmp, 270f)
                    else -> bmp
                }

                // Recupera i metadati dell'immagine
                val metadata = lastImageRef.metadata.await()
                val description = metadata.getCustomMetadata("description")

                withContext(Dispatchers.Main) {
                    _bitmapLiveData.postValue(rotatedBitmap)
                    _descriptionLiveData.postValue(description) // Imposta la descrizione nel LiveData
                }

            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Nessuna immagine trovata", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    fun downloadAllImages(context: Context) = CoroutineScope(Dispatchers.IO).launch {
        val child = if (sharedImplementation.getProfile() == "bistecca") {
            "bruschetta/"
        } else {
            "bistecca/"
        }
        try {
            val maxDownloadSize = 10L * 1024 * 1024
            val imageRefs = StorageUtil.imageRef.child(child).listAll().await()

            val images = mutableListOf<Bitmap>()

            for (imageRef in imageRefs.items) {
                val bytes = imageRef.getBytes(maxDownloadSize).await()

                // Leggi l'orientamento dell'immagine utilizzando ExifInterface
                val exif = ExifInterface(ByteArrayInputStream(bytes))
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )

                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                // Ruota la bitmap in base all'orientamento
                val rotatedBitmap = when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bmp, 90f)
                    ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bmp, 180f)
                    ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bmp, 270f)
                    else -> bmp
                }
                images.add(rotatedBitmap)
            }

            withContext(Dispatchers.Main) {
                if (images.isNotEmpty()) {
                    _allBitmapLiveData.postValue(images)
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Nessuna immagine trovata", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    fun realtimeUpdates(context: Context) {
        val child = if (sharedImplementation.getProfile() == "bistecca") {
            "bruschetta"
        } else {
            "bistecca"
        }
        val personalCollectionRef = Firebase.firestore.collection(child)
        personalCollectionRef.addSnapshotListener { querySnapShot, error ->
            error?.let {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            querySnapShot?.let {
                downloadLastImage(context)
            }
        }
    }
}
package gio.ado.bruschapp.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import gio.ado.bruschapp.SharedImplementation
import gio.ado.bruschapp.StorageUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

sealed class StatePhoto {
    object Loading : StatePhoto()
    object Success : StatePhoto()
    object Error : StatePhoto()
}

class ViewModel(context: Context) : ViewModel() {

    val saveScreen = MutableStateFlow("home")

    private val _bitmapLiveData = MutableLiveData<Bitmap?>()
    val bitmapLiveData: LiveData<Bitmap?> = _bitmapLiveData

    private val _allBitmapLiveData = MutableLiveData<List<Bitmap>>() // Cambia il tipo di LiveData
    val allBitmapLiveData: LiveData<List<Bitmap>> = _allBitmapLiveData


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

    fun downloadLastImage(context: Context) = CoroutineScope(Dispatchers.IO).launch {
            val child = if(sharedImplementation.getProfile() == "bistecca"){
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
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                _bitmapLiveData.postValue(bmp)

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
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                images.add(bmp)
            }

            if (images.isNotEmpty()) {
                _allBitmapLiveData.postValue(images)
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


    fun realtimeUpdates(context: Context){
        val child = if(sharedImplementation.getProfile() == "bistecca"){
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
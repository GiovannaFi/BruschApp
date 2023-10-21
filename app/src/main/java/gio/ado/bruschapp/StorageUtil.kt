package gio.ado.bruschapp

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class StorageUtil {
    companion object {
        private val storage = FirebaseStorage.getInstance()
        val imageRef = Firebase.storage.reference

        fun uploadToStorage(uri: Uri, context: Context, type: String, description: String) {
            val sharedImplementation = SharedImplementation(context)
            val storage = Firebase.storage
            val child = if (sharedImplementation.getProfile() == "bistecca") {
                "bistecca/"
            } else {
                "bruschetta/"
            }

            // Create a storage reference from our app
            val storageRef = storage.reference

            val unique_image_name = System.currentTimeMillis().toString()
            val spaceRef: StorageReference

            if (type == "image") {
                spaceRef = storageRef.child("$child$unique_image_name.jpg")
            } else {
                spaceRef = storageRef.child("videos/$unique_image_name.mp4")
            }

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            byteArray?.let {
                // Creare metadati personalizzati
                val metadata = StorageMetadata.Builder()
                    .setCustomMetadata("description", description)
                    .build()

                // Caricare l'immagine con metadati personalizzati
                val uploadTask = spaceRef.putBytes(byteArray, metadata)

                uploadTask.addOnFailureListener {
                    Toast.makeText(context, "Caricamento fallito", Toast.LENGTH_SHORT).show()
                    // Gestire i caricamenti non riusciti
                }.addOnSuccessListener { taskSnapshot ->
                    // Immagine caricata con successo
                    val downloadUrl = taskSnapshot.storage.downloadUrl
                    // Ora puoi salvare `downloadUrl` nel Firestore Database
                    saveImageUrlToFirestore(downloadUrl.toString(), context)
                    Toast.makeText(context, "Caricamento riuscito", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "Caricamento riuscito", Toast.LENGTH_SHORT).show()
                }
            }
        }


        private fun saveImageUrlToFirestore(imageUrl: String, context: Context) {
            val firestore = Firebase.firestore
            val collection = SharedImplementation(context).getProfile()
            val collectionRef =
                firestore.collection(collection.orEmpty()) // Sostituisci con il nome della tua collection Firestore

            // Crea un oggetto dati da salvare
            val data = hashMapOf("imageUrl" to imageUrl)

            // Aggiungi i dati al documento Firestore (puoi specificare un nome del documento o generare uno casuale)
            collectionRef.add(data)
                .addOnSuccessListener {
                    Log.e("DAJE", "APPO")
                }
                .addOnFailureListener { e ->
                    Log.e("NON DAJE", "NON APPO")
                }
        }

    }
}
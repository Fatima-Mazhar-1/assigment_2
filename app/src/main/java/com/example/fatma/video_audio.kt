package com.example.fatma

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioRecord
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class video_audio : AppCompatActivity() {

    private lateinit var storageReference: StorageReference
    private lateinit var selectedImageUri: Uri

    private var voiceNoteUri: Uri? = null
    private lateinit var audioRecord: AudioRecord
    private var isRecording = false
    private var mediaRecorder: MediaRecorder? = null

    private lateinit var audioFile: File
    var context: Context = this
    private lateinit var photoFile: File
    private lateinit var photoUri: Uri



    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_audio)



        storageReference = FirebaseStorage.getInstance().reference
        val selectPictureButton = findViewById<View>(R.id.btnSendPicture)
        selectPictureButton.setOnClickListener {
            openGalleryForImage()

        }
        val selectVideoButton = findViewById<View>(R.id.btnSendVideo)
        selectVideoButton.setOnClickListener {
            openGalleryForVideo()
        }
        val recordButton = findViewById<View>(R.id.btnSendAudio)
        recordButton.setOnClickListener {
            toggleRecording(it)
        }
        val button = findViewById<View>(R.id.btnSendPhoto)

        val camera = findViewById<CameraView>(R.id.camera)
        camera.setLifecycleOwner(this);
        camera.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                val maxWidth = 1080 // replace with your desired max width
                val maxHeight = 1920 // replace with your desired max height
                val file = File(context.filesDir, "photo.jpg") // replace with your file path



                result.toFile(file) {
                    // Handle the file result here
                    photoUri = Uri.fromFile(file)
                    uploadPhotoToFirebase()
                }
                val data = result.data // Access the raw data if needed
            }
        })

        // Call this method when you want to take a picture

        button.setOnClickListener {
            camera.takePicture()
        }
    }





    @RequiresApi(Build.VERSION_CODES.R)
    private fun toggleRecording(view: View) {
        if (view is Button) {
            if (!isRecording) {
                startRecording()
                view.text = "Stop Recording"
            } else {
                stopRecording()
                view.text = "Record Voice Note"
            }
            isRecording = !isRecording
        }
    }

    private fun openGalleryForVideo() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_VIDEO_REQUEST)
    }


    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun uploadImageToFirebase() {
        if (!::selectedImageUri.isInitialized) {
            return
        }
        val senderId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val fileName = UUID.randomUUID().toString()
        val ref = storageReference.child("images/$fileName")
        ref.putFile(selectedImageUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    val messageId = UUID.randomUUID().toString()
                    val pic = intent.getStringExtra("imageUrl")
                    val timestamp = getCurrentTimestamp()
                    val message = pic?.let { it1 ->
                        Message(messageId, "", senderId, timestamp,
                            it1, "picture", uri.toString())
                    }
                    val databaseRef = FirebaseDatabase.getInstance().getReference("messages").push()
                    databaseRef.setValue(message)
                }
            }
    }


    private fun getFormattedTime(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return format.format(date)
    }

    private fun getCurrentTimestamp(): String {
        return getFormattedTime(System.currentTimeMillis())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data!!
            uploadImageToFirebase()
        } else if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedVideoUri = data.data!!
            uploadVideoToFirebase(selectedVideoUri)
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            uploadPhotoToFirebase()
        }
    }

    private fun uploadPhotoToFirebase() {
        if (!::photoUri.isInitialized) {
            return
        }
        val senderId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val fileName = UUID.randomUUID().toString()
        val ref = storageReference.child("images/$fileName")
        ref.putFile(photoUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    val messageId = UUID.randomUUID().toString()
                    val pic = intent.getStringExtra("imageUrl")
                    val timestamp = getCurrentTimestamp()
                    val message = pic?.let { it1 ->
                        Message(messageId, "", senderId, timestamp,
                            it1, "picture", uri.toString())
                    }
                    val databaseRef = FirebaseDatabase.getInstance().getReference("messages").push()
                    databaseRef.setValue(message)
                }
            }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val PICK_VIDEO_REQUEST = 2
        private const val RECORD_AUDIO_PERMISSION_REQUEST_CODE = 121
        private val WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 101
        private const val PERMISSION_REQUEST_CODE = 200
        private const val REQUEST_IMAGE_CAPTURE = 3


    }


    private fun uploadVideoToFirebase(videoUri: Uri) {
        val senderId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val fileName = UUID.randomUUID().toString()
        val ref = storageReference.child("videos/$fileName")
        ref.putFile(videoUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    val messageId = UUID.randomUUID().toString()
                    val pic = intent.getStringExtra("imageUrl")
                    val videoUrl = uri.toString()
                    val timestamp = System.currentTimeMillis().toString()
                    val message = pic?.let { it1 ->
                        Message(messageId, "", senderId, timestamp,
                            it1, "video", videoUrl)
                    }
                    val databaseRef = FirebaseDatabase.getInstance().getReference("messages").push()
                    databaseRef.setValue(message)
                }
            }
    }



    @RequiresApi(Build.VERSION_CODES.R)
    private fun startRecording() {
        if (checkPermissions()) {
            mediaRecorder = MediaRecorder()

            val fileName = "recording_${System.currentTimeMillis()}.3gp"
            audioFile = File(context.filesDir, fileName)

            mediaRecorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setOutputFile(audioFile.path)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

                try {
                    prepare()
                    start()
                    isRecording = true
                } catch (e: IOException) {
                    Log.e(TAG, "prepare() failed")
                }
            }
        }
    }

    private fun stopRecording() {
        if (isRecording && mediaRecorder != null) {
            try {
                mediaRecorder?.apply {
                    stop()
                    release()
                }
            } catch (e: IllegalStateException) {
                Log.e(TAG, "Failed to stop and release MediaRecorder: ${e.message}")
            } finally {
                mediaRecorder = null
                isRecording = false  // Reset isRecording flag to false
                uploadVoiceNoteToFirebase(audioFile.name, FirebaseAuth.getInstance().currentUser?.uid ?: "")
            }
        }
    }






    private fun uploadVoiceNoteToFirebase(file: String, userId: String) {
        voiceNoteUri?.let { uri ->
            val ref = storageReference.child(audioFile.name)
            ref.putFile(uri)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { downloadUri ->
                        val pic = intent.getStringExtra("imageUrl")
                        val timestamp = System.currentTimeMillis().toString()
                        val type = "voice_note"
                        val messageid = UUID.randomUUID().toString()
                        val message = pic?.let { it1 ->
                            Message(messageid, "", userId, timestamp,
                                it1, type, downloadUri.toString())
                        }
                        val databaseRef = FirebaseDatabase.getInstance().getReference("messages").push()
                        databaseRef.setValue(message)
                    }
                }
        }
    }




    @RequiresApi(Build.VERSION_CODES.R)
    private fun checkPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE

                ),
                PERMISSION_REQUEST_CODE
            )
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    startRecording()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }







}

package com.example.fatma

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class twenty2page : AppCompatActivity() {
    private lateinit var fab: ImageButton

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { uri ->
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    userId?.let { uid ->
                        saveImageToInternalStorage(bitmap, uid)
                        fab.setImageBitmap(bitmap)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty2page)

        val mAuth = FirebaseAuth.getInstance()
        val mDatabase = FirebaseDatabase.getInstance().reference
        val displayNameEditText = findViewById<EditText>(R.id.name)
        val emailEditText = findViewById<EditText>(R.id.email)
        val contactNumberEditText = findViewById<EditText>(R.id.contact)
        val countryEditText = findViewById<EditText>(R.id.country)
        val cityEditText = findViewById<EditText>(R.id.city)
        val user: FirebaseUser? = mAuth.currentUser
        user?.uid?.let { uid ->
            mDatabase.child("users").child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val displayName = snapshot.child("name").value.toString()
                    val email = snapshot.child("email").value.toString()
                    val contactNumber = snapshot.child("contactNumber").value.toString()
                    val country = snapshot.child("country").value.toString()
                    val city = snapshot.child("city").value.toString()

                    displayNameEditText.setText(displayName)
                    emailEditText.setText(email)
                    contactNumberEditText.setText(contactNumber)
                    countryEditText.setText(country)
                    cityEditText.setText(city)
                    val imageUri = loadProfilePicture(uid)
                    imageUri?.let { uri ->
                        val bitmap = BitmapFactory.decodeFile(uri.path)
                        fab.setImageBitmap(bitmap)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

        val signUpButton = findViewById<View>(R.id.button)
        signUpButton.setOnClickListener {
            updateUserData(it)
        }

        fab = findViewById(R.id.fab)

        fab.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImage.launch(intent)
        }
    }

    private fun loadProfilePicture(userId: String): Uri? {
        val wrapper = ContextWrapper(applicationContext)
        val filePath = wrapper.getDir("images", Context.MODE_PRIVATE)
        val file = File(filePath, "image_$userId.jpg")
        return if (file.exists()) {
            Uri.parse(file.absolutePath)
        } else {
            null
        }
    }
    fun updateUserData(view: View) {
        val mAuth = FirebaseAuth.getInstance()
        val mDatabase = FirebaseDatabase.getInstance().reference
        val user: FirebaseUser? = mAuth.currentUser

        val displayNameEditText =findViewById<EditText>(R.id.name).text.toString()
        val emailEditText = findViewById<EditText>(R.id.email).text.toString()
        val  contactNumberEditText = findViewById<EditText>(R.id.contact).text.toString()
        val countryEditText = findViewById<EditText>(R.id.country).text.toString()
        val  cityEditText = findViewById<EditText>(R.id.city).text.toString()

        user?.uid?.let { uid ->
            val userData = mapOf(
                "name" to displayNameEditText,
                "email" to emailEditText,
                "contactNumber" to contactNumberEditText,
                "country" to countryEditText,
                "city" to cityEditText
            )
            mDatabase.child("users").child(uid).updateChildren(userData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun toTwenty1(view: View) {
        val intent = Intent(this,twenty1page::class.java)
        view.context.startActivity(intent)
        finish()
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap, userId: String): Uri? {
        val wrapper = ContextWrapper(applicationContext)
        val file = File(wrapper.getDir("images", Context.MODE_PRIVATE), "image_$userId.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
            return Uri.parse(file.absolutePath)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }



}

package com.example.fatma

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class signupmentor : AppCompatActivity() {

    private var mentorPictureUri: Uri? = null
    private val storage = FirebaseStorage.getInstance()

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            mentorPictureUri = uri
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupmentor)



        val signUpButton = findViewById<View>(R.id.button)
        signUpButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.editText).text.toString()
            val email = findViewById<EditText>(R.id.editText2).text.toString()
            val contactNumber = findViewById<EditText>(R.id.editText3).text.toString()
            val price = findViewById<EditText>(R.id.editText4).text.toString()
            val city = findViewById<EditText>(R.id.editText5).text.toString()
            val title = findViewById<EditText>(R.id.editText6).text.toString()
            saveMentor(name, email, contactNumber, price, city, title)
        }
        val signUpButton1 = findViewById<View>(R.id.button1)
        signUpButton1.setOnClickListener() {
            openGalleryForImage()
        }

    }

    fun saveMentor(
        mentorName: String,
        mentorEmail:String,
        mentorPhone:String,
        mentorPrice: String,
        mentorCategory: String,
        mentorTitle: String,
    ) {
        if (mentorName.isBlank()  || mentorPrice.isBlank() || mentorCategory.isBlank() || mentorTitle.isBlank()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val mentorId = FirebaseDatabase.getInstance().getReference("mentors").push().key
        if (mentorId == null) {
            Toast.makeText(this, "Failed to generate mentor ID", Toast.LENGTH_SHORT).show()
            return
        }

        if (mentorPictureUri != null) {
            val mentorRef = FirebaseDatabase.getInstance().getReference("mentors")
            val mentor = Mentor(
                mentorId,
                mentorName,
                mentorEmail,
                mentorPhone,
                mentorPrice,
                mentorCategory,
                mentorTitle,
                ""
            )
            val storageRef = storage.getReference("mentors").child(mentorId)
            storageRef.putFile(mentorPictureUri!!).addOnSuccessListener { taskSnapshot ->
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    mentor.picture = uri.toString()
                    mentorRef.child(mentorId).setValue(mentor)
                    startActivity(Intent(this, sevenpage::class.java))
                    finish()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
        }
    }



    fun toSeven(view: View) {
        val intent = Intent(this, sevenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }

    private fun openGalleryForImage() {
        galleryLauncher.launch("image/*")
    }
}

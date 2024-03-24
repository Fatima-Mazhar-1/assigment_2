package com.example.fatma

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.RatingBar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class elevenpage : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elevenpage)
        val name = intent.getStringExtra("name")
        val imageUrl = intent.getStringExtra("imageUrl")
        val nameTextView = findViewById<TextView>(R.id.name)
        nameTextView.text = "Hi, I am " + name

        val imageButton = findViewById<ImageButton>(R.id.image)
        Glide.with(this)
            .load(imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(imageButton)
        val button = findViewById<View>(R.id.submit)
        button.setOnClickListener {
            saveData()
        }
    }

    fun toTen(view: View) {
        val intent = Intent(this,tenpage::class.java)
        view.context.startActivity((intent))
        finish()
    }
    fun saveData() {
        val userID = FirebaseAuth.getInstance().currentUser?.uid
        val mentorName = intent.getStringExtra("name")

        val editText = findViewById<EditText>(R.id.editText)
        val data = editText.text.toString()

        val ratingBar = findViewById<RatingBar>(R.id.rating)
        val rating = ratingBar.rating.toInt()

        if (userID != null && mentorName != null) {
            val database = FirebaseDatabase.getInstance().reference
            val reviewsRef = database.child("reviews")
            val reviewData = mapOf("userID" to userID, "mentorName" to mentorName, "review" to data, "rating" to rating)
            reviewsRef.push().setValue(reviewData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                }
        }
    }



}
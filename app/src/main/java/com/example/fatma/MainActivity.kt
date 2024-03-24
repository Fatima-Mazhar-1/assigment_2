package com.example.fatma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.sign


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        Firebase.database.setPersistenceEnabled(true)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            val intent = Intent(this, secondpage
            ::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }




}
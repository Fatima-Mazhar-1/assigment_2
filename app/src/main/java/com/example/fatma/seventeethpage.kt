package com.example.fatma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class seventeethpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seventeethpage)
    }

    fun toeighteenth(view: View) {
        val intent = Intent(this,eighteenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }

    fun toTwelve(view: View) {
        val intent = Intent(this,seventeethpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
}
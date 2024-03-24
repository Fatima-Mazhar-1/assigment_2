package com.example.fatma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class twenty4page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty4page)
    }

    fun toSeven(view: View) {
        val intent = Intent(this,sevenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
}
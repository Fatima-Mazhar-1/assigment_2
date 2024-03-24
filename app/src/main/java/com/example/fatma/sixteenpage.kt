package com.example.fatma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class sixteenpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sixteenpage)
    }

    fun tonineteen(view: View) {
        val intent = Intent(this,nineteenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
    fun tofourteen(view: View) {
        val intent = Intent(this,fourteenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
    fun totwenty(view: View) {
        val intent = Intent(this,twentypage::class.java)
        view.context.startActivity(intent)
        finish()
    }
}
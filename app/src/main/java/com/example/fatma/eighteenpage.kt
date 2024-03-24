package com.example.fatma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class eighteenpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eighteenpage)
    }

    fun toseventeen(view: View) {
        val intent = Intent(this,seventeethpage::class.java)
        view.context.startActivity(intent)
        finish()
    }

    fun toTwelve(view: View) {
        val intent = Intent(this,twelvepage::class.java)
        view.context.startActivity(intent)
        finish()
    }
}
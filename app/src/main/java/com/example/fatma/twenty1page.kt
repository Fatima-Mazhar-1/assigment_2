package com.example.fatma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class twenty1page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty1page)
    }

    fun toTwenty3(view: View) {
        val intent = Intent(this,twenty3page::class.java)
        view.context.startActivity(intent)
        finish()
    }

    fun toTwenty2(view: View) {
        val intent = Intent(this,twenty2page::class.java)
        view.context.startActivity(intent)
        finish()
    }
    fun toSeven(view: View) {
        val intent = Intent(this,sevenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
}
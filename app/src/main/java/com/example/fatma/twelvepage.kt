package com.example.fatma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class twelvepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twelvepage)
    }

    fun toEighteen(view: View) {
        val intent = Intent(this,eighteenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
    fun toSeventeen(view: View) {
        val intent = Intent(this,seventeethpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
    fun toSeven(view: View) {
        val intent = Intent(this,sevenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
}
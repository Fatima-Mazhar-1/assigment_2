package com.example.fatma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class fourteenpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourteenpage)
    }

    fun tofifteen(view: View) {
        val intent = Intent(this,fifteenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
    fun toSixteen(view: View) {
        val intent = Intent(this,sixteenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
    fun toSeven(view: View) {
        val intent = Intent(this,sevenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
}
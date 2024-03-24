package com.example.fatma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View


class fourthpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourthpage)
    }

    fun backButton(view: View) {
        val intent = Intent(this,thirdpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
    fun toLogin(view:View){
        val intent = Intent(this ,secondpage::class.java)
        view.context.startActivity((intent))
        finish()
    }
}
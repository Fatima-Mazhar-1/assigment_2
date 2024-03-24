package com.example.fatma
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class twentypage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twentypage)
    }

    fun toThirteen(view: View) {
        val intent = Intent(this,thirteenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
}
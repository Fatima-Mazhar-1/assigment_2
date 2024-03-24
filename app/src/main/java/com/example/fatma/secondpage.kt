package com.example.fatma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class secondpage : AppCompatActivity() {

    private lateinit var firebaseManager: FirebaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondpage)

        firebaseManager = FirebaseManager(this)
        val signUpButton = findViewById<View>(R.id.button)
        signUpButton.setOnClickListener {
            onButtonClick()
        }


    }

    private fun onButtonClick() {
        val email = findViewById<EditText>(R.id.editText).text.toString()
        val password = findViewById<EditText>(R.id.editText2).text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseManager.signIn(email, password,
            {
                Toast.makeText(this, "Authentication success.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, sevenpage::class.java)
                startActivity(intent)
                finish()
            },
            { errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun onClickText(view: View) {
        val intent = Intent(this, thirdpage::class.java)
        startActivity(intent)
        finish()
    }

    fun toFifthpage(view: View) {
        val intent = Intent(this, fifthpage::class.java)
        startActivity(intent)
        finish()
    }

}

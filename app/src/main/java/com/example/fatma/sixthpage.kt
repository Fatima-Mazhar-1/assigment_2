package com.example.fatma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class sixthpage : AppCompatActivity() {
    private lateinit var newPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sixthpage)

        newPasswordEditText = findViewById(R.id.editText)
        confirmPasswordEditText = findViewById(R.id.editText1)
        auth = FirebaseAuth.getInstance()

        val resetButton: Button = findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (newPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (newPassword == confirmPassword) {
                    val user = auth.currentUser
                    if (user != null) {
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Password updated successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent = Intent(this, secondpage::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Failed to update password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            this,
                            "User not found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Passwords do not match",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Please enter both new password and confirm password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun backButton(view: View) {
        val intent = Intent(this, fifthpage::class.java)
        startActivity(intent)
        finish()
    }

    fun toLogin(view: View) {
        val intent = Intent(this, secondpage::class.java)
        startActivity(intent)
        finish()
    }
}

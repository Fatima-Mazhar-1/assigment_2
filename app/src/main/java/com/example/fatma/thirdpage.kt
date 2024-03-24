package com.example.fatma
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fatma.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.ktx.auth

class thirdpage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thirdpage)
        auth = FirebaseAuth.getInstance()

        val signUpButton = findViewById<View>(R.id.button)
        signUpButton.setOnClickListener {
            onClickText()
        }
    }


    private fun saveUserData(userId: String?, name: String, email: String, contactNumber: String,
                             country: String, city: String) {
        val database = FirebaseDatabase.getInstance().reference
        val userData = UserData(name, email, contactNumber, country, city)
        val userRef = database.child("users").child(userId ?: "")
        userRef.setValue(userData)
            .addOnSuccessListener {
                Log.d("SignUpActivity", "User data saved successfully")
            }
            .addOnFailureListener { e ->
                Log.w("SignUpActivity", "Error saving user data", e)
            }
    }

    data class UserData(val name: String, val email: String, val contactNumber: String,
                        val country: String, val city: String)

    private fun onClickText() {
        val name = findViewById<EditText>(R.id.editText).text.toString()
        val email = findViewById<EditText>(R.id.editText2).text.toString()
        val contactNumber = findViewById<EditText>(R.id.editText3).text.toString()
        val country = findViewById<EditText>(R.id.editText4).text.toString()
        val city = findViewById<EditText>(R.id.editText5).text.toString()
        val password = findViewById<EditText>(R.id.editText6).text.toString()

        if (name.isEmpty() || email.isEmpty() || contactNumber.isEmpty() || country.isEmpty() || city.isEmpty() || password.isEmpty()) {
            Toast.makeText(baseContext, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            return
        }


        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    saveUserData(user?.uid, name, email, contactNumber, country, city)
                    Toast.makeText(baseContext, "Sign up successful.", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, secondpage::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Sign up failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

package com.example.fatma
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class edit_delete : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_delete)
        val messageid = intent.getStringExtra("messageId")
        val message = intent.getStringExtra("message")
        val senderid = intent.getStringExtra("senderId")
        val timestamp = intent.getStringExtra("timestamp")
        val image = intent.getStringExtra("image")
        val type = intent.getStringExtra("type")
        val link = intent.getStringExtra("link")

        val deleteButton = findViewById<Button>(R.id.button2)
        deleteButton.setOnClickListener {
            deleteMessage(messageid)
        }

        val updateButton = findViewById<Button>(R.id.button1)
        val editTextMessage = findViewById<EditText>(R.id.editText)
        updateButton.setOnClickListener {
            val newMessage = editTextMessage.text.toString().trim()
            if (newMessage.isNotEmpty()) {
                if (type == "message") {
                    updateMessage(messageid, newMessage)
                } else {
                    Toast.makeText(this, "Cannot update non-message type", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteMessage(messageId: String?) {
        if (messageId != null) {
            val databaseRef = FirebaseDatabase.getInstance().getReference("messages").child(messageId)
            databaseRef.removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Message deleted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to delete message: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun updateMessage(messageId: String?, newMessage: String) {
        if (messageId != null) {
            val databaseRef = FirebaseDatabase.getInstance().getReference("messages").child(messageId)
            databaseRef.child("message").setValue(newMessage)
                .addOnSuccessListener {
                    Toast.makeText(this, "Message updated successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to update message: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

}

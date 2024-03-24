package com.example.fatma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class twenty3page : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var sessionAdapter: SessionAdapter
    private lateinit var used: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty3page)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val currentUser = FirebaseAuth.getInstance().currentUser
        used = currentUser?.uid ?: ""

        // Read data from Firebase
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("bookings")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val sessions = mutableListOf<Session>()
                for (dataSnapshot in snapshot.children) {
                    val session = dataSnapshot.getValue(Session::class.java)
                    session?.let {
                        if (used == it.id) {
                            sessions.add(it)
                        }
                    }
                }
                sessionAdapter = SessionAdapter(sessions)
                recyclerView.adapter = sessionAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Failed to read value.", error.toException())
            }
        })
    }


    fun toTwenty1(view: View) {
        val intent = Intent(this,twenty1page::class.java)
        view.context.startActivity(intent)
        finish()
    }
}
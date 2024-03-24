package com.example.fatma

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ninthpage : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ninthpage)

        database = FirebaseDatabase.getInstance().reference.child("mentors")

        val topCardRecyclerView: RecyclerView = findViewById(R.id.mentor_recycler)
        topCardRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val searchText = intent.getStringExtra("searchText")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cardTopArray = mutableListOf<Mentor>()

                for (childSnapshot in snapshot.children) {
                    val mentor = childSnapshot.getValue(Mentor::class.java)
                    if (mentor != null && searchText?.let { mentor.name.contains(it, true) } == true) {
                        cardTopArray.add(mentor)
                    }
                }

                topCardRecyclerView.adapter = SearchAdapter(cardTopArray)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun toEight(view: View) {
        val intent = Intent(this, eightpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
}

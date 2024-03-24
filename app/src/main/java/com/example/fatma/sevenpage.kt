package com.example.fatma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class sevenpage : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sevenpage)

        database = FirebaseDatabase.getInstance().reference.child("mentors")

        val topCardRecyclerView: RecyclerView = findViewById(R.id.recycler)
        topCardRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cardTopArray = mutableListOf<CardData>()

                for (childSnapshot in snapshot.children) {
                    val mentor = childSnapshot.getValue(Mentor::class.java)
                    mentor?.let {
                        cardTopArray.add(
                            CardData(
                                mentor.name,
                                mentor.title,
                                mentor.price,
                                mentor.picture
                            )
                        )
                    }
                }

                topCardRecyclerView.adapter = CardAdapter(cardTopArray.toTypedArray())
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun toNotifications(view: View) {
        val intent = Intent(this,twenty4page::class.java)
        view.context.startActivity(intent)
        finish()
    }

    fun toThirteen(view: View) {
        val intent = Intent(this,thirteenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }

    fun toTwenty1(view: View) {
        val intent = Intent(this,twenty1page::class.java)
        view.context.startActivity(intent)
        finish()
    }

    fun toSignupMentor(view: View) {
        val intent = Intent(this,signupmentor::class.java)
        view.context.startActivity(intent)
        finish()
    }
}

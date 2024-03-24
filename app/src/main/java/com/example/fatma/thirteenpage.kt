package com.example.fatma

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class thirteenpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thirteenpage)
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")
        val title = intent.getStringExtra("title")
        val imageUrl = intent.getStringExtra("imageUrl")
        val nameTextView = findViewById<TextView>(R.id.name)
        nameTextView.text = "Hi, I am " + name
        var date1 = ""
        val imageButton = findViewById<ImageButton>(R.id.image)
        Glide.with(this)
            .load(imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(imageButton)
        val date = findViewById<CalendarView>(R.id.date)
date.setOnDateChangeListener { view, year, month, dayOfMonth ->

          date1 =  ("$dayOfMonth/${month + 1}/$year")

        }


        val book = findViewById<View>(R.id.book)
        book.setOnClickListener(View.OnClickListener {
            if (name != null) {
                val session = Session()
                session.name = name

                if (title != null) {
                    session.title = title
                }
                if (imageUrl != null) {
                    session.picture = imageUrl
                }
                session.date=date1

                savebooking(session)
            }
        })
    }

    fun toTen(view: View) {
        val intent = Intent(this,tenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
    fun totwenty(view: View) {
        val intent = Intent(this,twentypage::class.java)
        view.context.startActivity(intent)
        finish()
    }
    fun tonineteen(view: View) {
        val intent = Intent(this,nineteenpage::class.java)
        view.context.startActivity(intent)
        finish()
    }
    fun toFifteen(view: View) {
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")
        val title = intent.getStringExtra("title")
        val imageUrl = intent.getStringExtra("imageUrl")
        val intent = Intent(this,fifteenpage::class.java)
        intent.putExtra("name", name)
        intent.putExtra("price", price)
        intent.putExtra("title", title)
        intent.putExtra("imageUrl", imageUrl)
        view.context.startActivity(intent)
        finish()
    }

    fun savebooking(session :Session){
        val ref = Firebase.database.getReference("bookings")
        val bookingId = ref.push().key
        if (bookingId != null) {
            val mAuth = FirebaseAuth.getInstance()
            session.id = mAuth.currentUser!!.uid
        }
        ref.child(bookingId!!).setValue(session)
    }
}
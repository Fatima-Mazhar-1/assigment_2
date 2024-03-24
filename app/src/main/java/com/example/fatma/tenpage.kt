package com.example.fatma

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class tenpage : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenpage)
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")
        val title = intent.getStringExtra("title")
        val imageUrl = intent.getStringExtra("imageUrl")
        val nameTextView = findViewById<TextView>(R.id.name)
        nameTextView.text = "Hi, I am " + name
        val button = findViewById<TextView>(R.id.eleven)
        button.setOnClickListener {
            if (name != null) {
                toEleven(it,name,imageUrl.toString())
            }
        }
        val imageButton = findViewById<ImageButton>(R.id.image)
        Glide.with(this)
            .load(imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(imageButton)
        val book = findViewById<View>(R.id.book)
        book.setOnClickListener(View.OnClickListener {
            if (name != null) {
                toThirteen(it,name,imageUrl.toString(),price.toString(),title.toString())
            }
        })

    }

    fun toNine(view: View) {
        val intent = Intent(this,ninthpage::class.java)
        view.context.startActivity((intent))
        finish()
    }

   private fun toEleven(view: View, name: String, toString: String) {
        val intent = Intent(this,elevenpage::class.java)
        intent.putExtra("name",name)
        intent.putExtra("imageUrl",toString)
        view.context.startActivity((intent))
        finish()
    }

  private  fun toThirteen(view: View,name: String,toString: String,price: String,title: String) {

        val intent = Intent(this,thirteenpage::class.java)
        intent.putExtra("name",name)
        intent.putExtra("imageUrl",toString)
        intent.putExtra("price",price)
        intent.putExtra("title",title)

        view.context.startActivity(intent)
        finish()
    }
}
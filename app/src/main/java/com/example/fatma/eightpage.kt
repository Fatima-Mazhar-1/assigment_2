package com.example.fatma
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView

class eightpage : AppCompatActivity() {
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eightpage)

        searchEditText = findViewById(R.id.search)
        searchEditText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, event ->
            if (actionId == KeyEvent.KEYCODE_ENTER || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                toNine()
                return@OnEditorActionListener true
            }
            false
        })
    }

    fun toSeven(view: View) {
        val intent = Intent(this, sevenpage::class.java)
        startActivity(intent)
        finish()
    }

    private fun toNine() {
        val searchText = searchEditText.text.toString()
        val intent = Intent(this, ninthpage::class.java)
        intent.putExtra("searchText", searchText)
        startActivity(intent)
        finish()
    }
}

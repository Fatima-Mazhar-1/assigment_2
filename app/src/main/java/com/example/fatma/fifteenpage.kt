package com.example.fatma
import okhttp3.*
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class fifteenpage : AppCompatActivity() {
    private lateinit var currentUser: String
    private lateinit var database: DatabaseReference
    private lateinit var editTextMessage: EditText

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifteenpage)
        currentUser = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        database = FirebaseDatabase.getInstance().reference.child("messages")
        editTextMessage = findViewById(R.id.editText7)
        val imageUrl = intent.getStringExtra("imageUrl")

        editTextMessage.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= editTextMessage.right - editTextMessage.totalPaddingRight) {
                        sendMessage(imageUrl)
                        return true
                    }
                    val drawableLeft = editTextMessage.compoundDrawablesRelative[0] ?: return false
                    val bounds = drawableLeft.bounds
                    if (event.rawX <= bounds.width() + editTextMessage.totalPaddingLeft) {
                        val intent = Intent(this@fifteenpage, video_audio::class.java)
                        intent.putExtra("imageUrl", imageUrl)
                        startActivity(intent)
                        return true
                    }
                }
                return false
            }
        })



        askNotificationPermission()
        val topCardRecyclerView: RecyclerView = findViewById(R.id.recyclerView25)
        topCardRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val message = mutableListOf<Message>()

                for (childSnapshot in snapshot.children) {
                    val message1 = childSnapshot.getValue(Message::class.java)
                    message1?.let {
                        message.add(
                            Message(
                                message1.messageId,
                                message1.message,
                                message1.senderId,
                                message1.timestamp,
                                message1.image,
                                message1.type,
                                message1.link
                            )
                        )
                    }
                }

                topCardRecyclerView.adapter = ChatAdapter(this@fifteenpage,message, currentUser)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }


    private fun getCurrentTimestamp(): String {
        return getFormattedTime(System.currentTimeMillis())
    }

    fun tofourteen(view: View) {
        val intent = Intent(this, fourteenpage::class.java)
        startActivity(intent)
        finish()
    }

    private fun getFormattedTime(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return format.format(date)
    }

    fun totwenty(view: View) {
        val intent = Intent(this, twentypage::class.java)
        startActivity(intent)
        finish()
    }

    fun sendMessage(imageUrl: String?) {
        val messageText = editTextMessage.text.toString().trim()
        if (messageText.isNotEmpty()) {
            val messageId = database.push().key ?: ""
            val timestamp = getCurrentTimestamp()
            val type = "message"

            val message = imageUrl?.let {
                Message(messageId, messageText, currentUser, timestamp,
                    it,type,""
                )
            }

            database.child(messageId).setValue(message)
            editTextMessage.text.clear()
        }
    }
    fun tonineteen(view: View) {
        val intent = Intent(this, nineteenpage::class.java)
        startActivity(intent)
        finish()
    }

    fun sendPushNotification(token: String, title: String, subtitle: String, body: String, data: Map<String, String> = emptyMap()) {
        val url = "https://fcm.googleapis.com/fcm/send"
        val bodyJson = JSONObject()
        bodyJson.put("to", token)
        bodyJson.put("notification",
            JSONObject().also {
                it.put("title", title)
                it.put("subtitle", subtitle)
                it.put("body", body)
                it.put("sound", "social_notification_sound.wav")
            }
        )
        if (data.isNotEmpty()) {
            bodyJson.put("data", JSONObject(data))
        }
        var key="AAAAS7pmhKU:APA91bFcieHcFqKjXYQe3z1UHgcsLBnOuhu8QYSV9F5IlZ_Nh8qtRteiwSQ7Rq9nZXGEZMOh7uFkeRmjIyPAftcMI99qZMXSBroOrQ-mpva31jB6E929-phYUvyoAnhTDvUwlMK23Lwv"
        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "key=$key")
            .post(
                bodyJson.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
            )
            .build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(
            object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    Log.d("TAG", "onResponse: ${response} ")
                }
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("TAG", "onFailure: ${e.message.toString()}")
                } } )
    }

    private fun askNotificationPermission() {
// This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
// FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
// TODO: display an educational UI explaining to the user the features that will be enabled
// by them granting the POST_NOTIFICATION permission. This UI should provide the user
// "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
// If the user selects "No thanks," allow the user to continue without notifications.

            } else {

                registerForActivityResult(ActivityResultContracts.RequestPermission()) {

                }
            }
        }

    }
}

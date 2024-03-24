package com.example.fatma

data class Message(
    val messageId: String = "",
    val message: String = "",
    val senderId: String = "",
    val timestamp: String = "",
    val image: String = "",
    val type: String = "",
    val link: String = ""

) {
    constructor() : this("", "", "", "", "","","")
}


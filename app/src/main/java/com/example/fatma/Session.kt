package com.example.fatma
data class Session(
    var id:String,
    var name: String,
    var title: String,
    var date: String,
    val time: String,
    var picture: String
){
    constructor() : this("","", "", "", "10:00", "")
}
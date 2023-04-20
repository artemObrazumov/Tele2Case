package com.example.tele2education.data.models

data class User(
    var id: String = "",
    var nickname: String = "",
    var birthDate: Long = 0L,
    var avatar: String = "",
    var balance: Int = 0,
    var form: Int = 0
)

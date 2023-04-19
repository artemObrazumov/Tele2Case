package com.example.tele2education.data.models

data class QuizItem(
    var question: String = "Сколько?",
    var options: ArrayList<String> = arrayListOf("1", "2", "3", "4"),
    var correctOption: Int = 0,
    var tip: String = "а",
    var score: Int = 0
)
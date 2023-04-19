package com.example.tele2education.data.models

data class QuizScript(
    var id: String = "",
    var quizItems: ArrayList<QuizItem> = arrayListOf(),
    var title: String = "",
    var description: String = "",
    var form: Int = 5,
    var subject: String = ""
)

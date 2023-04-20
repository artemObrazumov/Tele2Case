package com.example.tele2education.data.models

data class QuizRoom (
    var id: String = "",
    var quizId: String = "",
    var participants: List<QuizParticipant> = emptyList(),
    var adminId: String = "",
    var script: List<QuizItem> = emptyList(),
    var state: Int = 0
)
package com.example.tele2education.data.models

data class QuizRoom (
    var id: String,
    var participants: List<QuizParticipant>,
    var script: List<QuizItem>
)
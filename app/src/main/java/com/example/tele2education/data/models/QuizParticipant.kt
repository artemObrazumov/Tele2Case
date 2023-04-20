package com.example.tele2education.data.models

data class QuizParticipant (
    var userId: String = "",
    var state: Int = 1
) {
    companion object {
        const val STATE_JOINED = 1
        const val STATE_READY = 2
    }
}
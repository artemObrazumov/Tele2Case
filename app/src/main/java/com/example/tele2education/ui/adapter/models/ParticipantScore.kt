package com.example.tele2education.ui.adapter.models

import com.example.tele2education.data.models.User

data class ParticipantScore(
    var participant: User,
    var score: Int = 0
)

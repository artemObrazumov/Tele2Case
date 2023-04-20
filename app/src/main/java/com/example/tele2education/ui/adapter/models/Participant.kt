package com.example.tele2education.ui.adapter.models

import com.example.tele2education.data.models.User

data class Participant(
    var userData: User = User(),
    var state: Int = 0
)

package com.example.tele2education.ui.game_preparing

interface GamePrepareEventListener {
    fun onPlayerJoined(id: String)
    fun onPlayerLeft(id: String)
    fun onPlayerStateChanged(id: String, state: Int)
    fun onQuizStarted()
}
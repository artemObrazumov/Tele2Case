package com.example.tele2education.ui.game_finished

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import com.example.tele2education.ui.adapter.models.ParticipantScore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameFinishedViewModel: ViewModel() {
    val users: MutableLiveData<List<ParticipantScore>> = MutableLiveData()

    fun loadUsers(roomId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            users.postValue(App.api.loadBestPlayers(roomId))
        }
    }

    fun saveScoreToBalance(score: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            App.api.saveScoreToBalance(score)
        }
    }
}
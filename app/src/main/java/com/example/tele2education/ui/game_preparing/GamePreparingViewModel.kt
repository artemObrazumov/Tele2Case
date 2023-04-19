package com.example.tele2education.ui.game_preparing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import com.example.tele2education.ui.adapter.models.Participant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GamePreparingViewModel : ViewModel() {
    val newRoomId: MutableLiveData<String> = MutableLiveData()
    val newParticipant: MutableLiveData<Participant> = MutableLiveData()

    fun attachToChanges(roomId: String, listener: GamePrepareEventListener) {
        App.api.connectPrepareListener(roomId, listener)
    }

    fun createRoom(quizId: String, questionCounter: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val roomId = App.api
                .createRoom(App.api.getCurrentUser()!!.uid, quizId, questionCounter)
            newRoomId.postValue(roomId)
        }
    }

    fun loadParticipant(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userData = App.api.getUser(id)
        }
    }
}
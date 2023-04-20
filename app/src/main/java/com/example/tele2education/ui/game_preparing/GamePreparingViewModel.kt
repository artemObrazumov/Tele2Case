package com.example.tele2education.ui.game_preparing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import com.example.tele2education.data.models.QuizParticipant
import com.example.tele2education.ui.adapter.models.Participant
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GamePreparingViewModel : ViewModel() {
    val newRoomId: MutableLiveData<String> = MutableLiveData()
    val newParticipant: MutableLiveData<Participant> = MutableLiveData()
    val roomUsers: MutableLiveData<List<Participant>> = MutableLiveData()

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
            newParticipant.postValue(Participant(userData, QuizParticipant.STATE_JOINED))
        }
    }

    fun addToRoom(uid: String, roomId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            App.api.addToRoom(uid, roomId)
        }
    }

    fun loadRoomUsers(roomId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var users = App.api.loadRoomUsers(roomId)
            roomUsers.postValue(users)
        }
    }

    fun deleteRoom(roomId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseDatabase.getInstance().getReference("quiz_room/$roomId").removeValue()
        }
    }

    fun leaveRoom(roomId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = App.api.getCurrentUser()!!.uid
            FirebaseDatabase.getInstance()
                .getReference("quiz_room/$roomId/participants/$uid").removeValue()
        }
    }

    fun removeParticipant(index: Int, roomId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseDatabase.getInstance()
                .getReference("quiz_room/$roomId/participants/$index").removeValue()
        }
    }

    fun startQuiz(roomId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseDatabase.getInstance()
                .getReference("quiz_room/$roomId/state").setValue(1)
        }
    }
}
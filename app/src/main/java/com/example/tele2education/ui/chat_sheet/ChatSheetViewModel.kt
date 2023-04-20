package com.example.tele2education.ui.chat_sheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import com.example.tele2education.data.models.Message
import com.example.tele2education.data.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatSheetViewModel: ViewModel() {
    val chat: MutableLiveData<List<Message>> = MutableLiveData()
    val userData: MutableLiveData<User> = MutableLiveData()

    fun loadChat(roomId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            chat.postValue(App.api.loadChat(roomId))
        }
    }

    fun loadUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            userData.postValue(App.api.getUser(App.api.getCurrentUser()!!.uid))
        }
    }

    fun attachToUpdates(roomId: String, onNewMessage: (message: Message) -> Unit) {
        App.api.attachToUpdates(roomId, onNewMessage)
    }

    fun uploadMessage(message: Message) {
        App.api.uploadMessage(message)
    }
}
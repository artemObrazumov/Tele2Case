package com.example.tele2education.ui.in_game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import com.example.tele2education.data.models.QuizItem
import com.example.tele2education.ui.adapter.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InGameViewModel: ViewModel() {
    val tasks: MutableLiveData<List<Task>> = MutableLiveData()

    fun loadTasks(roomId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tasks.postValue(App.api.loadTasks(roomId))
        }
    }
}
package com.example.tele2education.ui.game_info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import com.example.tele2education.data.models.Quiz
import com.example.tele2education.data.models.QuizRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameInfoViewModel : ViewModel() {
    val rooms: MutableLiveData<List<QuizRoom>> = MutableLiveData()
    val quiz: MutableLiveData<Quiz> = MutableLiveData()
    fun loadRooms(quizId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            rooms.postValue(App.api.getRooms(quizId))
        }
    }

    fun loadQuiz(quizId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            quiz.postValue(App.api.getQuiz(quizId))
        }
    }
}
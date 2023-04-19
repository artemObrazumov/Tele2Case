package com.example.tele2education.ui.competitions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import com.example.tele2education.data.models.Quiz
import kotlinx.coroutines.launch

class CompetitionsViewModel : ViewModel() {
    val quizs: MutableLiveData<ArrayList<Quiz>> = MutableLiveData()

    fun loadQuizs() {
        viewModelScope.launch {
            val form = "6"
            val quizs = App.api.getQuizs().apply { sortBy { form == it.form } }
            this@CompetitionsViewModel.quizs.postValue(quizs)
        }
    }
}
package com.example.tele2education.ui.education.education_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import com.example.tele2education.data.models.education.EducationLesson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EducationLessonViewModel : ViewModel() {
    val lessons: MutableLiveData<ArrayList<EducationLesson>> = MutableLiveData()

    fun getLessons(form: Int, id: String){
        viewModelScope.launch(Dispatchers.IO){
            val data = App.api.getEducationLessonItems(form, id)
            lessons.postValue(data)
        }
    }
}
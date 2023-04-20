package com.example.tele2education.ui.education.education_detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import com.example.tele2education.data.models.education.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InteractiveEducationViewModel: ViewModel() {
    val result_data: MutableLiveData<ArrayList<Any>> = MutableLiveData()

    fun getInteractiveEducationItems(id: String, form: Int, id_lesson: String){
        val data: ArrayList<Any> = arrayListOf()

        viewModelScope.launch(Dispatchers.IO) {
            val result = App.api.getInteractiveEducationItems(form, id, id_lesson)
            result.forEach {
                when (it) {
                    is Theory        -> data.add(it)
                    is TestTask      -> data.add(it)
                    is DictationTask -> data.add(it)
                    is SentenceTask  -> data.add(it)
                    else -> Log.d("viewModel.data", "error!")
                }
            }
            result_data.postValue(result)
        }
    }
}
package com.example.tele2education.ui.education

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EducationViewModel : ViewModel() {

    private val data: MutableLiveData<String> = MutableLiveData()

    init {
        getData()
    }

    private fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            val data = App.api.getEducationItems(5)
            Log.d("viewModelEducation", data)
        }
    }
}
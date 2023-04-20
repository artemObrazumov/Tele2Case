package com.example.tele2education.ui.education


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import com.example.tele2education.data.models.education.Education
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EducationViewModel : ViewModel() {

    val arrData: MutableLiveData<ArrayList<Education>> = MutableLiveData()

    fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            val data = App.api.getEducationItems(5)
            arrData.postValue(data)
        }
    }
}
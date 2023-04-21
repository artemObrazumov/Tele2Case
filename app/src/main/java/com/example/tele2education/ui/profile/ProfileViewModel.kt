package com.example.tele2education.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import com.example.tele2education.data.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    val userData: MutableLiveData<User> = MutableLiveData()
    fun loadUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            userData.postValue(App.api.getUser(App.api.getCurrentUser()!!.uid))
        }
    }
}
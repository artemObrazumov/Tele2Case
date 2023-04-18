package com.borsch_team.moneysaver.ui.auth.reset_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import com.example.tele2education.data.models.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthResetPasswordViewModel: ViewModel() {
    val authResultData: MutableLiveData<AuthResult> = MutableLiveData()

    fun resetPassword(email: String){
        viewModelScope.launch(Dispatchers.IO) {
            App.api.resetPassword(email) {
                authResultData.postValue(it)
            }
        }
    }
}
package com.example.tele2education.ui.finish_registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2education.App
import com.example.tele2education.data.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FinishRegistrationViewModel: ViewModel() {
    fun uploadUserData(user: User, onUploadFinished: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            App.api.uploadUserData(user) {
                onUploadFinished()
            }
        }
    }
}
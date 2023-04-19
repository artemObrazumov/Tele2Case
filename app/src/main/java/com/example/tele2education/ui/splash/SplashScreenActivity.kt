package com.example.tele2education.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.borsch_team.moneysaver.ui.auth.sign_up.AuthSignUpActivity
import com.example.tele2education.App
import com.example.tele2education.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (App.api.getCurrentUser() == null) {
            startActivity(Intent(this, AuthSignUpActivity::class.java))
            finish()
        }
        if (!App.preferencesManager.getRegisterStatus()) {
            startActivity(Intent(this, AuthSignUpActivity::class.java))
            finish()
        }
    }
}
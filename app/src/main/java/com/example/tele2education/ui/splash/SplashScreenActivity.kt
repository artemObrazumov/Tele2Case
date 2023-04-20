package com.example.tele2education.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.borsch_team.moneysaver.ui.auth.sign_in.AuthSignInActivity
import com.borsch_team.moneysaver.ui.auth.sign_up.AuthSignUpActivity
import com.example.tele2education.App
import com.example.tele2education.MainActivity
import com.example.tele2education.databinding.ActivitySplashScreenBinding
import com.example.tele2education.ui.finish_registration.FinishRegistrationActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (App.api.getCurrentUser() == null) {
            startActivity(Intent(this, AuthSignInActivity::class.java))
        } else if (!App.preferencesManager.getRegisterStatus()) {
            startActivity(Intent(this, FinishRegistrationActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
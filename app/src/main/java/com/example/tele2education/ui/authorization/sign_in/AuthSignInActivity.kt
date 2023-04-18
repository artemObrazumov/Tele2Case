package com.borsch_team.moneysaver.ui.auth.sign_in

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.ui.auth.reset_password.AuthResetPasswordActivity
import com.borsch_team.moneysaver.ui.auth.sign_up.AuthSignUpActivity
import com.example.tele2education.MainActivity
import com.example.tele2education.databinding.ActivityAuthSignInBinding

class AuthSignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthSignInBinding
    private lateinit var viewModel: AuthSignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        //setupTheme()
        super.onCreate(savedInstanceState)

        binding = ActivityAuthSignInBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AuthSignInViewModel::class.java]

        binding.btnSignIn.setOnClickListener {
            val email = "" + binding.inputEmail.text
            val password = "" + binding.inputPassword.text
            if(email != "" && password != ""){
                viewModel.authResultData.observe(this){
                    if(it.isSuccessful){
                        startActivity(Intent(this, MainActivity::class.java).apply {
                            putExtra("afterLogin", true)
                        })
                    }else{
                        Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
                    }
                }
                viewModel.signInWithEmail(email, password)
            }else{
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, AuthResetPasswordActivity::class.java))
        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, AuthSignUpActivity::class.java))
        }

        setContentView(binding.root)
    }
}
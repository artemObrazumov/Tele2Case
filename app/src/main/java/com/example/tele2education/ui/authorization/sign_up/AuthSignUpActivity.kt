package com.borsch_team.moneysaver.ui.auth.sign_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tele2education.MainActivity
import com.example.tele2education.databinding.ActivityAuthSignUpBinding
import com.example.tele2education.ui.finish_registration.FinishRegistrationActivity

class AuthSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthSignUpBinding
    private lateinit var viewModel: AuthSignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthSignUpBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AuthSignUpViewModel::class.java]

        binding.btnSignUp.setOnClickListener {
            val email = "" + binding.inputEmail.text
            val password = "" + binding.inputPassword.text
            val repeatPassword = "" + binding.inputRepeatPassword

            if(email != "" && password != "" && repeatPassword != ""){
                viewModel.authResultData.observe(this){
                    if(it.isSuccessful){
                        startActivity(Intent(this, FinishRegistrationActivity::class.java).apply {
                            putExtra("afterLogin", true)
                        })
                    }else{
                        Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
                    }
                }
                viewModel.signUpWithEmail(email, password)
            }else{
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
        setContentView(binding.root)
    }
}
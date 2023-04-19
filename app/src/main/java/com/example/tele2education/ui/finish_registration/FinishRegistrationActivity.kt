package com.example.tele2education.ui.finish_registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tele2education.databinding.ActivityFinishRegistrationBinding

class FinishRegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishRegistrationBinding
    private lateinit var viewModel: FinishRegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishRegistrationBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[FinishRegistrationViewModel::class.java]
        setContentView(binding.root)
    }
}
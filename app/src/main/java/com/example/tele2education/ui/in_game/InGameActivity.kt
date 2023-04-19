package com.example.tele2education.ui.in_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tele2education.databinding.ActivityInGameBinding

class InGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInGameBinding
    private lateinit var viewModel: InGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInGameBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[InGameViewModel::class.java]
        binding.back.setOnClickListener {
            onBackPressed()
        }
        setContentView(binding.root)
    }
}
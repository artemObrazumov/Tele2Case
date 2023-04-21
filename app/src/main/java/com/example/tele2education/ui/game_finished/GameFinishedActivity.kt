package com.example.tele2education.ui.game_finished

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tele2education.App
import com.example.tele2education.databinding.ActivityGameFinishedBinding
import com.example.tele2education.ui.adapter.UsersFinishedAdapter

class GameFinishedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameFinishedBinding
    private lateinit var viewModel: GameFinishedViewModel
    private lateinit var adapter: UsersFinishedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameFinishedBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[GameFinishedViewModel::class.java]
        viewModel.loadUsers(intent.getStringExtra("roomId")!!)
        viewModel.users.observe(this) {
            adapter.setDataset(it)
            viewModel.saveScoreToBalance(it.find {
                it.participant.id == App.api.getCurrentUser()!!.uid
            }!!.score)
        }
        adapter = UsersFinishedAdapter(emptyList())
        binding.participantsList.layoutManager = LinearLayoutManager(this)
        binding.participantsList.adapter = adapter
        binding.exit.setOnClickListener {
            onBackPressed()
        }
        setContentView(binding.root)
    }
}
package com.example.tele2education.ui.game_preparing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tele2education.App
import com.example.tele2education.databinding.ActivityGamePreparingBinding
import com.example.tele2education.ui.adapter.ParticipantsAdapter

class GamePreparingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGamePreparingBinding
    private lateinit var viewModel: GamePreparingViewModel
    private var roomId: String? = ""
    private var questionCounter: Int = 3
    private var quizId: String = "quiz1"
    private lateinit var listener: GamePrepareEventListener
    private lateinit var adapter: ParticipantsAdapter
    private var usersInRoom: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createListener()
        binding = ActivityGamePreparingBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[GamePreparingViewModel::class.java]
        viewModel.newRoomId.observe(this) {
            roomId = it
            viewModel.loadParticipant(App.api.getCurrentUser()!!.uid)
            viewModel.attachToChanges(roomId!!, listener)
        }
        viewModel.newParticipant.observe(this) {
            adapter.addParticipant(it)
            usersInRoom++
            binding.readyCounter.text = "Участников готово: $usersInRoom/4"
        }
        roomId = intent.getStringExtra("roomId")
        if (roomId == null) {
            viewModel.createRoom(quizId, questionCounter)
        } else {
            viewModel.attachToChanges(roomId!!, listener)
        }
        setContentView(binding.root)
    }

    private fun createListener() {
        listener = object: GamePrepareEventListener {
            override fun onPlayerJoined(id: String) {
                viewModel.loadParticipant(id)
            }

            override fun onPlayerLeft(id: String) {
                adapter.removePlayer(id)
                usersInRoom--
            }

            override fun onPlayerStateChanged(id: String, state: Int) {
                TODO("Not yet implemented")
            }
        }
    }
}
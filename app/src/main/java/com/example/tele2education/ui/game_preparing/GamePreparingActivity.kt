package com.example.tele2education.ui.game_preparing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tele2education.App
import com.example.tele2education.databinding.ActivityGamePreparingBinding
import com.example.tele2education.ui.adapter.ParticipantsAdapter
import com.example.tele2education.ui.chat_sheet.ChatSheetFragment
import com.example.tele2education.ui.in_game.InGameActivity
import com.google.android.material.snackbar.Snackbar

class GamePreparingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGamePreparingBinding
    private lateinit var viewModel: GamePreparingViewModel
    private var roomId: String? = null
    private var roomAdmin: String = App.api.getCurrentUser()!!.uid
    private var questionCounter: Int = 5
    private var quizId: String = "quiz1"
    private lateinit var listener: GamePrepareEventListener
    private lateinit var adapter: ParticipantsAdapter
    private var usersInRoom: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createListener()
        binding = ActivityGamePreparingBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[GamePreparingViewModel::class.java]
        getIntentData()
        adapter = ParticipantsAdapter(emptyList(), roomAdmin) {
            viewModel.removeParticipant(it, roomId!!)
        }
        binding.participantsList.adapter = adapter
        binding.participantsList.layoutManager = LinearLayoutManager(this)
        binding.chat.setOnClickListener {
            ChatSheetFragment(roomId!!).show(supportFragmentManager, "chat")
        }
        viewModel.newRoomId.observe(this) {
            roomId = it
            viewModel.attachToChanges(roomId!!, listener)
        }
        viewModel.roomUsers.observe(this) {
            viewModel.attachToChanges(roomId!!, listener)
            adapter.setDataset(it)
        }
        viewModel.newParticipant.observe(this) {
            adapter.addParticipant(it)
            usersInRoom = adapter.itemCount
            binding.readyCounter.text = "Участников готово: $usersInRoom/4"
        }
        if (roomId == null) {
            viewModel.createRoom(quizId, questionCounter)
            roomAdmin = App.api.getCurrentUser()!!.uid
        }
        if (roomAdmin == App.api.getCurrentUser()!!.uid) {
            binding.startButton.visibility = View.VISIBLE
            binding.startButton.setOnClickListener {
                if (usersInRoom < 1) {
                    Snackbar
                        .make(binding.root, "Недостаточно участников!", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else {
                    viewModel.startQuiz(roomId!!)
                }
            }
        } else {
            binding.waitMessage.visibility = View.VISIBLE
        }
        setContentView(binding.root)
    }

    private fun getIntentData() {
        quizId = intent.getStringExtra("quiz")!!
        val intentId = intent.getStringExtra("id")
        val adminId = intent.getStringExtra("admin")
        if (intentId != null) {
            roomId = intentId
            viewModel.addToRoom(App.api.getCurrentUser()!!.uid, roomId!!)
            viewModel.loadRoomUsers(roomId!!)
        }
        if (adminId != null) {
            roomAdmin = adminId
        }
    }

    private fun createListener() {
        listener = object: GamePrepareEventListener {
            override fun onPlayerJoined(id: String) {
                viewModel.loadParticipant(id)
            }

            override fun onPlayerLeft(id: String) {
                if (id == App.api.getCurrentUser()!!.uid) {
                    finish()
                    onBackPressed()
                }
                adapter.removePlayer(id)
                usersInRoom = adapter.itemCount
            }

            override fun onPlayerStateChanged(id: String, state: Int) {}

            override fun onQuizStarted() {
                startActivity(
                    Intent(this@GamePreparingActivity, InGameActivity::class.java)
                        .apply { putExtra("roomId", roomId!!) }
                )
            }
        }
    }

    override fun onBackPressed() {
        if (roomAdmin == App.api.getCurrentUser()!!.uid) {
            viewModel.deleteRoom(roomId!!)
        } else {
            viewModel.leaveRoom(roomId!!)
        }
        super.onBackPressed()
    }
}
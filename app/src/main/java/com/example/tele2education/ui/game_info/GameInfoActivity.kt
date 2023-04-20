package com.example.tele2education.ui.game_info

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tele2education.databinding.ActivityGameInfoBinding
import com.example.tele2education.ui.adapter.QuizRoomsAdapter
import com.example.tele2education.ui.game_preparing.GamePreparingActivity

class GameInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameInfoBinding
    private lateinit var viewModel: GameInfoViewModel
    private lateinit var adapter: QuizRoomsAdapter
    private var quizId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameInfoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[GameInfoViewModel::class.java]
        quizId = intent.getStringExtra("quizId")!!
        binding.startButton.setOnClickListener {
            startActivity(Intent(this, GamePreparingActivity::class.java))
        }
        adapter = QuizRoomsAdapter(emptyList()) { id, admin ->
            startActivity(Intent(this, GamePreparingActivity::class.java).apply {
                putExtra("id", id)
                putExtra("admin", admin)
            })
        }
        binding.roomsList.layoutManager = LinearLayoutManager(this)
        binding.roomsList.adapter = adapter
        viewModel.rooms.observe(this) { rooms ->
            adapter.setDataset(rooms)
        }
        viewModel.loadRooms(quizId)

        setContentView(binding.root)
    }
}
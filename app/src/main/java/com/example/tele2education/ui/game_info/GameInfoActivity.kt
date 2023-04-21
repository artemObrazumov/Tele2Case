package com.example.tele2education.ui.game_info

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tele2education.R
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
        binding.back.setOnClickListener {
            onBackPressed()
        }
        viewModel = ViewModelProvider(this)[GameInfoViewModel::class.java]
        quizId = intent.getStringExtra("quizId")!!
        binding.startButton.setOnClickListener {
            startActivity(Intent(this, GamePreparingActivity::class.java).apply {
                putExtra("quiz", quizId)
            })
        }
        adapter = QuizRoomsAdapter(emptyList()) { id, admin ->
            startActivity(Intent(this, GamePreparingActivity::class.java).apply {
                putExtra("id", id)
                putExtra("admin", admin)
                putExtra("quiz", quizId)
            })
        }
        binding.roomsList.layoutManager = LinearLayoutManager(this)
        binding.roomsList.adapter = adapter
        viewModel.rooms.observe(this) { rooms ->
            adapter.setDataset(rooms)
        }
        viewModel.quiz.observe(this) {
            Glide.with(binding.root).load(it.picture).into(binding.gamePicture)
            binding.quiztitle.text = it.title
            binding.gameDescription.text = it.description
            binding.content.animate().alpha(1f).duration=500L
            if (it.form == "0") {
                binding.form.text = "Для самых маленьких"
            } else if (it.form == "100") {
                binding.form.text = "Для самых старших"
            } else {
                binding.form.text = "${it.form} класс"
            }
            binding.subject.text = it.subject
            when(it.subject) {
                "Математика", "Информатика", "Физика" -> {
                    binding.subjectCircle.setImageDrawable(
                        ContextCompat.getDrawable(
                        binding.root.context, R.drawable.category_item_circle_tech
                    ))
                    binding.subjectBar
                        .setBackgroundResource(R.drawable.category_item_background_tech)
                }
                "География", "Биология" -> {
                    binding.subjectCircle.setImageDrawable(
                        ContextCompat.getDrawable(
                        binding.root.context, R.drawable.category_item_circle_natural
                    ))
                    binding.subjectBar
                        .setBackgroundResource(R.drawable.category_item_background_natural)
                }
                "История", "Литература" -> {
                    binding.subjectCircle.setImageDrawable(
                        ContextCompat.getDrawable(
                        binding.root.context, R.drawable.category_item_circle_soc
                    ))
                    binding.subjectBar
                        .setBackgroundResource(R.drawable.category_item_background_soc)
                }
            }
        }
        viewModel.loadRooms(quizId)
        viewModel.loadQuiz(quizId)

        setContentView(binding.root)
    }
}
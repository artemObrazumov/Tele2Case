package com.example.tele2education.ui.in_game

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.tele2education.App
import com.example.tele2education.databinding.ActivityInGameBinding
import com.example.tele2education.ui.adapter.QuestionNumbersAdapter
import com.example.tele2education.ui.adapter.TaskAdapter
import com.example.tele2education.ui.game_finished.GameFinishedActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class InGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInGameBinding
    private lateinit var viewModel: InGameViewModel
    private lateinit var adapter: TaskAdapter
    private lateinit var numberAdapter: QuestionNumbersAdapter
    private var roomId: String? = null
    private lateinit var countdownTimer: CountDownTimer
    private var speedBonus: Int = 0
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseDatabase.getInstance()
            .getReference("quiz_progress/$roomId/${App.api.getCurrentUser()!!.uid}")
            .setValue(0)
        binding = ActivityInGameBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[InGameViewModel::class.java]
        roomId = intent.getStringExtra("roomId")
        binding.back.setOnClickListener {
            onBackPressed()
        }
        adapter = TaskAdapter(emptyList()) {
            if (it) {
                addScore()
                speedBonus--
                updateSpeedBonus()
            }
        }
        numberAdapter = QuestionNumbersAdapter(adapter.itemCount, 0)
        binding.questionNumbers.adapter = numberAdapter
        viewModel.tasks.observe(this) { tasks ->
            adapter.setDataset(tasks)
            numberAdapter.updateCounter(tasks.size)
        }
        binding.questionsPager.adapter = adapter
        viewModel.loadTasks(roomId!!)
        binding.questionsPager.isUserInputEnabled = false
        binding.questionsPager.clipToPadding = false
        binding.questionsPager.setPadding(40, 0, 40, 0)
        setContentView(binding.root)
        startFirstCountdown()
        FirebaseDatabase.getInstance().getReference("quiz_room/$roomId/speedBonus")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        speedBonus = snapshot.getValue(Int::class.java)!!
                    } catch (_: Exception) {}
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun addScore() {
        if (speedBonus == 4) {
            score += 5+5
        }
        else if (speedBonus == 3) {
            score += 5+3
        }
        else if (speedBonus == 2) {
            score += 5+2
        } else {
            score += 5
        }
        FirebaseDatabase.getInstance()
            .getReference("quiz_progress/$roomId/${App.api.getCurrentUser()!!.uid}")
            .setValue(score)
    }

    private fun updateSpeedBonus() {
        FirebaseDatabase.getInstance()
            .getReference("quiz_room/$roomId/speedBonus").setValue(speedBonus)
    }

    private fun startFirstCountdown() {
        countdownTimer = object : CountDownTimer(13000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                val timeDigits = if (secondsLeft < 10) "0$secondsLeft" else secondsLeft.toString()
                binding.time.text = "00:$timeDigits"
            }
            override fun onFinish() {
                onTimerFinished()
            }
        }
        countdownTimer.start()
    }

    private fun onTimerFinished() {
        val holder = (binding.questionsPager.get(0) as RecyclerView)
            .findViewHolderForAdapterPosition(binding.questionsPager.currentItem)
                as TaskAdapter.ViewHolder
        holder.showCorrect()
        updateScore()
        startDelayTimer()
        numberAdapter.completeToNext()
    }

    private fun updateScore() {
        binding.score.text = "Набрано баллов: $score"
    }

    private fun startDelayTimer() {
        countdownTimer = object : CountDownTimer(5000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                val timeDigits = if (secondsLeft < 10) "0$secondsLeft" else secondsLeft.toString()
                binding.time.text = "00:$timeDigits"
            }
            override fun onFinish() {
                onDelayTimerFinished()
            }
        }
        countdownTimer.start()
    }

    private fun onDelayTimerFinished() {
        startCountdown()
        if (binding.questionsPager.currentItem == 4) {
            startActivity(Intent(this, GameFinishedActivity::class.java).apply {
                putExtra("roomId", roomId!!)
            })
            FirebaseDatabase.getInstance().getReference("quiz_room/$roomId").removeValue()
            finish()
        } else {
            FirebaseDatabase.getInstance()
                .getReference("quiz_room/$roomId/speedBonus")
                .setValue(4)
        }
        binding.questionsPager.currentItem++
    }

    private fun startCountdown() {
        countdownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                val timeDigits = if (secondsLeft < 10) "0$secondsLeft" else secondsLeft.toString()
                binding.time.text = "00:$timeDigits"
            }
            override fun onFinish() {
                onTimerFinished()
            }
        }
        countdownTimer.start()
    }
}
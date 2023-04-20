package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tele2education.R
import com.example.tele2education.data.models.Message
import com.example.tele2education.data.models.QuizItem
import com.example.tele2education.data.models.QuizParticipant
import com.example.tele2education.data.models.QuizRoom
import com.example.tele2education.databinding.GameItemBinding
import com.example.tele2education.databinding.MessageItemBinding
import com.example.tele2education.databinding.TaskItemBinding
import com.example.tele2education.databinding.UserItemBinding
import com.example.tele2education.ui.adapter.models.Participant
import com.example.tele2education.ui.adapter.models.Task

class TaskAdapter(
    private var tasks: List<Task>,
): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    class ViewHolder(private val binding: TaskItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(task: Task, index: Int) {
            val adapter = AnswersAdapter(task.quizTask.options)
            binding.answers.layoutManager = LinearLayoutManager(binding.root.context)
            binding.answers.adapter = adapter
            binding.question.text = "$index\n\n${task.quizTask.question}"
            binding.tip.text = task.quizTask.tip
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TaskItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position], position+1)
    }

    override fun getItemCount() = tasks.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}
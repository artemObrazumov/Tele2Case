package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tele2education.R
import com.example.tele2education.databinding.TaskItemBinding
import com.example.tele2education.ui.adapter.models.Task

class TaskAdapter(
    private var tasks: List<Task>,
    private var onTaskSolved: (isCorrect: Boolean) -> Unit
): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    class ViewHolder(private val binding: TaskItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        var task: Task = Task()
        var selectedOption = -1
        var adapter: AnswersAdapter? = null
        @SuppressLint("SetTextI18n")
        fun bind(task: Task, index: Int,
                 onTaskSolved: (isCorrect: Boolean) -> Unit) {
            this.task = task
            adapter = AnswersAdapter(task.quizTask.options) { it, clickedView ->
                if (!task.hasChecked) {
                    selectedOption = it
                    task.hasChecked = true
                    onTaskSolved(it-1 == task.quizTask.correctOption)
                    clickedView.setBackgroundResource(R.drawable.answer_selected)
                }
            }
            binding.answers.layoutManager = LinearLayoutManager(binding.root.context)
            binding.answers.adapter = adapter
            binding.question.text = "$index\n\n${task.quizTask.question}"
            binding.tip.text = task.quizTask.tip

            binding.tip.visibility = View.GONE
            binding.tipIcon.visibility = View.GONE
        }

        fun showCorrect() {
            binding.tip.visibility = View.VISIBLE
            binding.tipIcon.visibility = View.VISIBLE
            if (selectedOption != -1) {
                (binding.answers.findViewHolderForAdapterPosition(selectedOption-1)
                        as AnswersAdapter.ViewHolder)
                    .markWrong()
            }
            (binding.answers.findViewHolderForAdapterPosition(task.quizTask.correctOption)
                    as AnswersAdapter.ViewHolder)
                .markRight()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TaskItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position], position+1) {
            onTaskSolved(it)
        }
    }

    override fun getItemCount() = tasks.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}
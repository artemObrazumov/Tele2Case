package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tele2education.data.models.Quiz
import com.example.tele2education.databinding.QuizItemBinding

class QuizItemsAdapter(
    private var quizItems: List<Quiz>,
    private val onQuizItemClicked: (item: Quiz) -> Unit
): RecyclerView.Adapter<QuizItemsAdapter.ViewHolder>() {
    class ViewHolder(private val binding: QuizItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(quiz: Quiz,
                 onQuizItemClicked: (item: Quiz) -> Unit) {
            binding.root.setOnClickListener {
                onQuizItemClicked(quiz)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(QuizItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quizItems[position]) { item -> onQuizItemClicked(item) }
    }

    override fun getItemCount() = quizItems.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(quizs: List<Quiz>) {
        quizItems = quizs
        notifyDataSetChanged()
    }
}
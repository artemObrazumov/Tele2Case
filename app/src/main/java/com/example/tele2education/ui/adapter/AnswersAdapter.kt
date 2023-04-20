package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tele2education.databinding.AnswerItemBinding

class AnswersAdapter(
    private var answers: List<String>
): RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {
    class ViewHolder(private val binding: AnswerItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(answer: String, index: Int) {
            binding.answerNumber.text = "$index."
            binding.answerContent.text = answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AnswerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(answers[position], position+1)
    }

    override fun getItemCount() = answers.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(answers: List<String>) {
        this.answers = answers
        notifyDataSetChanged()
    }
}
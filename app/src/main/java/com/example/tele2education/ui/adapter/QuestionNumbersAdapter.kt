package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tele2education.databinding.QuestionNumberItemBinding

class QuestionNumbersAdapter(
    private val questions: Int,
    private val completedTo: Int
): RecyclerView.Adapter<QuestionNumbersAdapter.ViewHolder>() {
    class ViewHolder(private val binding: QuestionNumberItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(position: Int, isCompleted: Boolean) {
            if (isCompleted) {
                binding.number.text = (position+1).toString()
                binding.check.visibility = View.GONE
            } else {
                binding.number.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(QuestionNumberItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, position+1 <= completedTo)
    }

    override fun getItemCount() = questions-1
}
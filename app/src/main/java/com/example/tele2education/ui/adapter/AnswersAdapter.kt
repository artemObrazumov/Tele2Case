package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tele2education.R
import com.example.tele2education.databinding.AnswerItemBinding

class AnswersAdapter(
    private var answers: List<String>,
    private var onItemSelected: (index: Int, view: View) -> Unit
): RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {
    class ViewHolder(private val binding: AnswerItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(answer: String, index: Int, onItemSelected: (index: Int, view: View) -> Unit) {
            binding.answerNumber.text = "$index."
            binding.answerContent.text = answer
            binding.root.setOnClickListener {
                onItemSelected(index, it)
            }
        }

        fun markWrong() {
            binding.root.setBackgroundResource(R.drawable.answer_wrong)
        }

        fun markRight() {
            binding.root.setBackgroundResource(R.drawable.answer_right)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AnswerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(answers[position], position+1) { item, view ->
            onItemSelected(item, view)
        }
    }

    override fun getItemCount() = answers.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(answers: List<String>) {
        this.answers = answers
        notifyDataSetChanged()
    }
}
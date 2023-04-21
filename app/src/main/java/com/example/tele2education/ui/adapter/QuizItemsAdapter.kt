package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tele2education.R
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
            binding.title.text = quiz.title
            Glide.with(binding.root)
                .load(quiz.picture)
                .into(binding.gamePicture)
            binding.subject.text = quiz.subject
            if (quiz.form == "0") {
                binding.form.text = "Для самых маленьких"
            } else if (quiz.form == "100") {
                binding.form.text = "Для самых старших"
            } else {
                binding.form.text = "${quiz.form} класс"
            }
            when(quiz.subject) {
                "Математика", "Информатика", "Физика" -> {
                    binding.subjectCircle.setImageDrawable(ContextCompat.getDrawable(
                        binding.root.context, R.drawable.category_item_circle_tech
                    ))
                    binding.subjectBar
                        .setBackgroundResource(R.drawable.category_item_background_tech)
                }
                "География", "Биология" -> {
                    binding.subjectCircle.setImageDrawable(ContextCompat.getDrawable(
                        binding.root.context, R.drawable.category_item_circle_natural
                    ))
                    binding.subjectBar
                        .setBackgroundResource(R.drawable.category_item_background_natural)
                }
                "История", "Литература" -> {
                    binding.subjectCircle.setImageDrawable(ContextCompat.getDrawable(
                        binding.root.context, R.drawable.category_item_circle_soc
                    ))
                    binding.subjectBar
                        .setBackgroundResource(R.drawable.category_item_background_soc)
                }
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
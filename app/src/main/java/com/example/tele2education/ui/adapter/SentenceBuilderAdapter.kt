package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tele2education.R
import com.example.tele2education.data.models.education.SentenceItem
import com.example.tele2education.data.models.education.SentenceTask
import com.example.tele2education.data.models.education.SentenceWord
import com.example.tele2education.databinding.SentenceBuilderItemBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class SentenceBuilderAdapter(
    private val onWordTipClicked: (word: String) -> Unit
): RecyclerView.Adapter<SentenceBuilderAdapter.ViewHolder>() {

    private var dataSet = emptyList<SentenceItem>()

    class ViewHolder(private val binding: SentenceBuilderItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var adapter: WordClickableAdapter
        private lateinit var task: SentenceItem

        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        fun bind(
            sentenceItem: SentenceItem,
            onWordTipClicked: (word: SentenceWord) -> Unit,
            index: Int
        ) {
            task = sentenceItem
            val new_arr: ArrayList<SentenceWord> = arrayListOf()
            sentenceItem.words.forEach {
                new_arr.add(SentenceWord(it, false))
            }
            adapter = WordClickableAdapter(
                onWordTipClicked,
                {onWordInserted(it.word)},
                { binding.words.post { it.run() }  },
                binding.enechSentence)
            adapter.setDataSet(new_arr)

            binding.words.layoutManager =
                FlexboxLayoutManager(itemView.context, FlexDirection.ROW, FlexWrap.WRAP)
            binding.words.adapter = adapter
            binding.russianSentence.text = "$index: ${sentenceItem.answer}"
            binding.refresh.setOnClickListener {
                binding.enechSentence.text = ""
                binding.words.post {
                    adapter.refresh()
                }
            }
        }

        @SuppressLint("SetTextI18n")
        private fun onWordInserted(word: String) {
            binding.enechSentence.text = "${binding.enechSentence.text} $word"
        }

        fun checkSentence(): Boolean {
            val isCorrect = binding.enechSentence.text.trim() == task.correctSentence
            binding.checkResult.visibility = View.VISIBLE
            binding.enechSentence.setTextColor(ContextCompat.getColor(binding.root.context,
                if (isCorrect) R.color.green else R.color.red)
            )
            if (!isCorrect) {
                binding.checkResult.text = task.correctSentence
            }
            binding.refresh.setOnClickListener(null)
            binding.refresh.isClickable = false
            binding.refresh.isFocusable = false
            deactivateWordButtons()
            return isCorrect
        }

        private fun deactivateWordButtons() {
            for (i in 0 until adapter.itemCount) {
                val holder = binding.words.findViewHolderForAdapterPosition(i)
                        as WordClickableAdapter.ViewHolder
                holder.deactivateWordButton()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            SentenceBuilderItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sentenceItem = dataSet[position]
        holder.bind(sentenceItem, { onWordTipClicked(it.word) }, (position+1))
    }

    override fun getItemCount(): Int = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataSet(task: SentenceTask) {
        dataSet = task.sentences
        notifyDataSetChanged()
    }
}
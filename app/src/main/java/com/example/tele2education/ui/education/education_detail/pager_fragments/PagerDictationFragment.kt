package com.example.tele2education.ui.education.education_detail.pager_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arcticapp.ui.adapters.WordCompareAdapter
import com.example.tele2education.ui.adapter.WordDraggableAdapter
import com.example.tele2education.data.models.education.DictationTask
import com.example.tele2education.databinding.FragmentPagerDictationBinding
import com.example.tele2education.ui.adapter.DragListener
import kotlin.math.max


class PagerDictationFragment(val dictationTask: DictationTask) : Fragment() {

    private lateinit var binding: FragmentPagerDictationBinding
    private lateinit var wordsAdapter: WordDraggableAdapter
    private lateinit var wordsCompareAdapter: WordCompareAdapter
    private var taskID: String = ""
    private var score = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerDictationBinding.inflate(layoutInflater)

        wordsAdapter = WordDraggableAdapter()
        binding.wordsList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.wordsList.adapter = wordsAdapter
        binding.wordsList.setOnDragListener(DragListener())
        wordsCompareAdapter = WordCompareAdapter(
            { word, position ->
                val holder = binding.compareList.findViewHolderForAdapterPosition(position)
                        as WordCompareAdapter.ViewHolder
                holder.setTranslationLabel("Перевод: $word")
                holder.animateCorrectly()
                onWordComparingDone()
            },
            { position ->
                val holder = binding.compareList.findViewHolderForAdapterPosition(position)
                        as WordCompareAdapter.ViewHolder
                holder.setTranslationLabel("Неверный перевод!")
                holder.animateWrongly()
                score = max(score - 1, 0)
            }
        )
        binding.compareList.layoutManager = LinearLayoutManager(requireContext())
        binding.compareList.adapter = wordsCompareAdapter
        binding.compareList.setOnDragListener(DragListener())

        wordsAdapter.setDataset(dictationTask.words)
        score = dictationTask.words.size
        wordsCompareAdapter.setDataset(dictationTask.correctWords)


        return binding.root
    }

    private fun finishTask(score: Int) {
        val maxScore = wordsCompareAdapter.itemCount

    }

    private fun onWordComparingDone() {
        if (isDone()) {
            finishTask(score)
        }
    }

    private fun isDone(): Boolean = wordsAdapter.itemCount == 1

}
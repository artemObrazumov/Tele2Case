package com.example.tele2education.ui.education.education_detail.pager_fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tele2education.R
import com.example.tele2education.data.models.education.SentenceTask
import com.example.tele2education.databinding.FragmentPagerSentenceBinding
import com.example.tele2education.ui.adapter.SentenceBuilderAdapter


class PagerSentenceFragment(val sentenceTask: SentenceTask) : Fragment() {

    private lateinit var binding: FragmentPagerSentenceBinding
    private lateinit var adapter: SentenceBuilderAdapter
    private var taskID: String = ""
    private var checked = false
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerSentenceBinding.inflate(layoutInflater)
        adapter = SentenceBuilderAdapter { word ->
            Log.d("asdassd", "asda")
        }
        binding.sentences.layoutManager = LinearLayoutManager(requireContext())
        binding.sentences.adapter = adapter
        adapter.setDataSet(sentenceTask)
        binding.check.setOnClickListener {
            if (!checked) {
                checkTest()
                checked = true
            } else {
                finishTask(score)
            }
        }

        return binding.root
    }

    private fun checkTest() {
        for (i in 0 until adapter.itemCount) {
            val holder = binding.sentences.findViewHolderForAdapterPosition(i)
                    as SentenceBuilderAdapter.ViewHolder
            if (holder.checkSentence()) {
                score++
            }
        }
        binding.check.text = requireActivity().getString(R.string.finish)
        binding.check.icon = null
    }

    private fun finishTask(score: Int) {
        val maxScore = adapter.itemCount

    }

}
package com.example.tele2education.ui.education.education_detail.pager_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tele2education.data.models.education.SentenceTask
import com.example.tele2education.databinding.FragmentPagerSentenceBinding


class PagerSentenceFragment(sentenceTask: SentenceTask) : Fragment() {

    private lateinit var binding: FragmentPagerSentenceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerSentenceBinding.inflate(layoutInflater)

        return binding.root
    }
}
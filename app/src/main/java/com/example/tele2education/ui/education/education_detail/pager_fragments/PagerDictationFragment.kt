package com.example.tele2education.ui.education.education_detail.pager_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tele2education.data.models.education.DictationTask
import com.example.tele2education.databinding.FragmentPagerDictationBinding


class PagerDictationFragment(dictationTask: DictationTask) : Fragment() {

    private lateinit var binding: FragmentPagerDictationBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerDictationBinding.inflate(layoutInflater)



        return binding.root
    }

}
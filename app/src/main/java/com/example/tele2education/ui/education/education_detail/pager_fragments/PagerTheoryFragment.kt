package com.example.tele2education.ui.education.education_detail.pager_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tele2education.R
import com.example.tele2education.data.models.education.Theory
import com.example.tele2education.databinding.FragmentPagerTheoryBinding


class PagerTheoryFragment(val theory: Theory) : Fragment() {

    private lateinit var binding: FragmentPagerTheoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerTheoryBinding.inflate(layoutInflater)
        return binding.root
    }
}
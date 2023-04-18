package com.example.tele2education.ui.competitions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tele2education.R
import com.example.tele2education.databinding.FragmentCompetitionsBinding

class CompetitionsFragment : Fragment() {

    private lateinit var binding: FragmentCompetitionsBinding
    private lateinit var viewModel: CompetitionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompetitionsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CompetitionsViewModel::class.java]


        return binding.root
    }

}
package com.example.tele2education.ui.education

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tele2education.databinding.FragmentEducationBinding

class EducationFragment : Fragment() {

    private lateinit var binding: FragmentEducationBinding
    private lateinit var viewModel: EducationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEducationBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[EducationViewModel::class.java]


        return binding.root
    }
}
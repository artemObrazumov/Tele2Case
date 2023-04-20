package com.example.tele2education.ui.education.education_detail

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tele2education.R
import com.example.tele2education.databinding.FragmentEducationLessonBinding
import com.example.tele2education.ui.adapter.EducationLessonRecyclerAdapter

class EducationLessonFragment : Fragment() {

    private lateinit var binding: FragmentEducationLessonBinding
    private lateinit var viewModel: EducationLessonViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEducationLessonBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[EducationLessonViewModel::class.java]

        val id = arguments?.getString("id")
        val form = arguments?.getInt("form")

        binding.recyclerViewLessons.layoutManager = LinearLayoutManager(requireContext())
        val adapter = EducationLessonRecyclerAdapter{
            val intent = Intent(requireContext(), InteractiveEducationActivity::class.java)
            intent.putExtra("form", form)
            intent.putExtra("id", id)
            intent.putExtra("id_lesson", it.id)
            startActivity(intent)
        }
        viewModel.lessons.observe(requireActivity()){
            adapter.setDataList(it)
            binding.recyclerViewLessons.adapter = adapter
        }
        viewModel.getLessons(form!!, id!!)

        return binding.root
    }

}
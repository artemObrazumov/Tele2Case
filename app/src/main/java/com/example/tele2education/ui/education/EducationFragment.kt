package com.example.tele2education.ui.education

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tele2education.R
import com.example.tele2education.databinding.FragmentEducationBinding
import com.example.tele2education.ui.adapter.EducationRecyclerAdapter

class EducationFragment : Fragment() {

    private lateinit var binding: FragmentEducationBinding
    private lateinit var viewModel: EducationViewModel
    private var form: Int = 5

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEducationBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[EducationViewModel::class.java]

        binding.recyclerEducation.layoutManager = LinearLayoutManager(context)
        val adater = EducationRecyclerAdapter{
            Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
            val bundle = bundleOf("id" to it.id, "form" to form)
             findNavController().navigate(R.id.navigation_education_lesson, bundle)
        }

        viewModel.arrData.observe(viewLifecycleOwner){
            adater.setDataList(it)
            binding.recyclerEducation.adapter = adater
        }
        viewModel.getData()

        return binding.root
    }
}
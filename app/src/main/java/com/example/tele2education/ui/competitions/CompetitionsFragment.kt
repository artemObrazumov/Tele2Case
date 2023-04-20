package com.example.tele2education.ui.competitions

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tele2education.R
import com.example.tele2education.databinding.FragmentCompetitionsBinding
import com.example.tele2education.ui.adapter.QuizItemsAdapter
import com.example.tele2education.ui.game_info.GameInfoActivity

class CompetitionsFragment : Fragment() {

    private lateinit var binding: FragmentCompetitionsBinding
    private lateinit var viewModel: CompetitionsViewModel
    private lateinit var adapter: QuizItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompetitionsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CompetitionsViewModel::class.java]
        adapter = QuizItemsAdapter(emptyList()) { quiz ->
            startActivity(Intent(requireContext(), GameInfoActivity::class.java).apply {
                putExtra("quizId", quiz.id)
            })
        }
        binding.quizList.layoutManager = LinearLayoutManager(requireContext())
        binding.quizList.adapter = adapter
        viewModel.quizs.observe(viewLifecycleOwner) { quizs ->
            adapter.setDataset(quizs)
        }
        viewModel.loadQuizs()

        return binding.root
    }

}
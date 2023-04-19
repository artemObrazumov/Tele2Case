package com.example.tele2education.ui.game_info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tele2education.databinding.FragmentGameInfoBinding
import com.example.tele2education.databinding.FragmentHomeBinding
import com.example.tele2education.ui.game_preparing.GamePreparingActivity
import com.example.tele2education.ui.in_game.InGameActivity

class GameInfoFragment : Fragment() {

    private lateinit var binding: FragmentGameInfoBinding
    private lateinit var viewModel: GameInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameInfoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[GameInfoViewModel::class.java]
        binding.startButton.setOnClickListener {
            startActivity(Intent(requireActivity(), GamePreparingActivity::class.java))
        }

        return binding.root
    }
}
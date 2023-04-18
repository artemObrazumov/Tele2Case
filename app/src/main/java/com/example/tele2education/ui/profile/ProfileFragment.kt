package com.example.tele2education.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tele2education.R
import com.example.tele2education.databinding.FragmentHomeBinding
import com.example.tele2education.databinding.FragmentProfileBinding
import com.example.tele2education.ui.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        binding.btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }

        return binding.root
    }

}
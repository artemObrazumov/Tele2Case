package com.example.tele2education.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tele2education.databinding.FragmentProfileBinding
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
        viewModel.userData.observe(viewLifecycleOwner) { user ->
            binding.name.text = user.nickname
            binding.balance.text = user.balance.toString()
            binding.form.text = user.form.toString()
            binding.age.text = user.birthDate.toString()
        }
        viewModel.loadUserData()
        return binding.root
    }

}
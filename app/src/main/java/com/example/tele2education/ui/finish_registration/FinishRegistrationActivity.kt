package com.example.tele2education.ui.finish_registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.ViewModelProvider
import com.example.tele2education.App
import com.example.tele2education.MainActivity
import com.example.tele2education.data.models.User
import com.example.tele2education.databinding.ActivityFinishRegistrationBinding

class FinishRegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishRegistrationBinding
    private lateinit var viewModel: FinishRegistrationViewModel
    private var ageSelected: Int = 0
    private var formSelected: Int = 0
    private var dateOfBirth: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishRegistrationBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[FinishRegistrationViewModel::class.java]
        setupTabSelection()
        setContentView(binding.root)
        binding.finish.setOnClickListener {
            viewModel.uploadUserData(User(
                App.api.getCurrentUser()!!.uid,
                binding.name.text.toString().trim(),
                dateOfBirth,
                "",
                0,
                formSelected
            )) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    private fun setupTabSelection() {
        binding.optionLower11.setOnClickListener {
            ageSelected = 0
            updateTabSelection(0)
        }
        binding.option11.setOnClickListener {
            ageSelected = 11
            updateTabSelection(1)
        }
        binding.option12.setOnClickListener {
            ageSelected = 12
            updateTabSelection(2)
        }
        binding.option13.setOnClickListener {
            ageSelected = 13
            updateTabSelection(3)
        }
        binding.option14.setOnClickListener {
            ageSelected = 14
            updateTabSelection(4)
        }
        binding.option15.setOnClickListener {
            ageSelected = 15
            updateTabSelection(5)
        }
        binding.option16.setOnClickListener {
            ageSelected = 16
            updateTabSelection(6)
        }
        binding.optionHigher16.setOnClickListener {
            ageSelected = 100
            updateTabSelection(7)
        }


        binding.optionLower5.setOnClickListener {
            formSelected = 0
            updateFormTabSelection(0)
        }
        binding.option5.setOnClickListener {
            formSelected = 5
            updateFormTabSelection(1)
        }
        binding.option6.setOnClickListener {
            formSelected = 6
            updateFormTabSelection(2)
        }
        binding.option7.setOnClickListener {
            formSelected = 7
            updateFormTabSelection(3)
        }
        binding.option8.setOnClickListener {
            formSelected = 8
            updateFormTabSelection(4)
        }
        binding.option9.setOnClickListener {
            formSelected = 9
            updateFormTabSelection(5)
        }
        binding.optionHigher9.setOnClickListener {
            formSelected = 100
            updateFormTabSelection(6)
        }
    }

    private fun updateTabSelection(newTabIndex: Int) {
        val width = binding.optionLower11.width
        binding.selectBackground.animate().x((width * newTabIndex).toFloat()).duration = 150L
    }

    private fun updateFormTabSelection(newTabIndex: Int) {
        val width = binding.optionLower5.width
        binding.selectBackground2.animate().x((width * newTabIndex).toFloat()).duration = 150L
    }
}
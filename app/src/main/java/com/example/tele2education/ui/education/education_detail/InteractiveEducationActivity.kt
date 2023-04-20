package com.example.tele2education.ui.education.education_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.tele2education.R
import com.example.tele2education.data.models.education.*
import com.example.tele2education.databinding.ActivityInteractiveEducationBinding
import com.example.tele2education.ui.adapter.EducationViewPagerAdapter
import com.example.tele2education.ui.education.education_detail.pager_fragments.PagerDictationFragment
import com.example.tele2education.ui.education.education_detail.pager_fragments.PagerSentenceFragment
import com.example.tele2education.ui.education.education_detail.pager_fragments.PagerTestFragment
import com.example.tele2education.ui.education.education_detail.pager_fragments.PagerTheoryFragment
import com.google.android.material.tabs.TabLayoutMediator

class InteractiveEducationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInteractiveEducationBinding
    private lateinit var viewModel: InteractiveEducationViewModel
    private var dataSet: ArrayList<Any> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInteractiveEducationBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[InteractiveEducationViewModel::class.java]

        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        val form = intent.getIntExtra("form", 0)
        val id_lesson = intent.getStringExtra("id_lesson")

        val adapter = EducationViewPagerAdapter(supportFragmentManager, lifecycle)

        viewModel.result_data.observe(this){
            adapter.setDataList(it)
            dataSet = it
            binding.viewPager.adapter = adapter

            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (dataSet[position]) {
                    is Theory        -> tab.setIcon(R.drawable.baseline_menu_book_24)
                    is TestTask      -> tab.setIcon(R.drawable.baseline_checklist_rtl_24)
                    is DictationTask -> tab.setIcon(R.drawable.baseline_checklist_rtl_24)
                    is SentenceTask  -> tab.setIcon(R.drawable.baseline_checklist_rtl_24)
                    else -> Log.d("viewModel.data", "error!")
                }
            }.attach()
        }
        viewModel.getInteractiveEducationItems(id!!, form, id_lesson!!)
    }
}
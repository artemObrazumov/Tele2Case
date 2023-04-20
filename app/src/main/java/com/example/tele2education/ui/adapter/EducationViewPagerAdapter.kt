package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tele2education.data.models.education.*
import com.example.tele2education.ui.education.education_detail.pager_fragments.PagerDictationFragment
import com.example.tele2education.ui.education.education_detail.pager_fragments.PagerSentenceFragment
import com.example.tele2education.ui.education.education_detail.pager_fragments.PagerTestFragment
import com.example.tele2education.ui.education.education_detail.pager_fragments.PagerTheoryFragment

class EducationViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

    private var dataList = emptyList<Any>()

    @SuppressLint("NotifyDataSetChanged")
    internal fun setDataList(dataList: ArrayList<Any>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun createFragment(position: Int): Fragment {
        when (dataList[position]) {
            is Theory        -> return PagerTheoryFragment(dataList[position] as Theory)
            is TestTask      -> return PagerTestFragment(dataList[position] as TestTask)
            is DictationTask -> return PagerDictationFragment(dataList[position] as DictationTask)
            is SentenceTask  -> return PagerSentenceFragment(dataList[position] as SentenceTask)
            else -> {
                Log.d("viewModel.data", "error!")
                return Fragment()
            }
        }
    }
}
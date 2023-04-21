package com.example.tele2education.ui.education.education_detail.pager_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arcticapp.ui.adapters.TestAdapter
import com.example.tele2education.R
import com.example.tele2education.data.models.education.TestTask
import com.example.tele2education.databinding.FragmentPagerTestBinding


class PagerTestFragment(private val testTask: TestTask) : Fragment() {

    private lateinit var binding: FragmentPagerTestBinding
    private lateinit var adapter: TestAdapter
    private var taskID: String = ""
    private var checked = false
    private var score = 0
    private var isCompleate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerTestBinding.inflate(layoutInflater)

        adapter = TestAdapter()
        adapter.setDataset(testTask.test_items)
        binding.tests.layoutManager = LinearLayoutManager(requireContext())
        binding.tests.adapter = adapter


        binding.check.setOnClickListener {
            if(isCompleate){
                binding.check.setBackgroundColor(R.drawable.background_line_block)
            }else{
                if (!checked) {
                    checkTest()
                    checked = true
                } else {
                    if (score == adapter.itemCount){
                        // Набрали макс баллов, можем идти дальше
                        isCompleate = true
                        binding.check.visibility = View.INVISIBLE
                    }else{

                    }
                    //finishTask(score)
                }
            }

        }

        return binding.root
    }

    private fun checkTest() {
        for (i in 0 until adapter.itemCount) {
            val holder = binding.tests.findViewHolderForAdapterPosition(i)
                    as TestAdapter.ViewHolder
            if (holder.checkTask()) {
                score++
            }
        }
        binding.check.text = requireActivity().getString(R.string.finish)
        binding.check.icon = null
    }

    private fun finishTask(score: Int) {
        val maxScore = adapter.itemCount
        if (maxScore == score){
            binding.check.text = "Продолжить"
        }
        /*startActivity(
            Intent(requireContext(), CongratulationsActivity::class.java)
            .apply {
                putExtra("score", score)
                putExtra("maxscore", maxScore)
                //putExtra("type", TaskAdapter.TASK_TEST)
                putExtra("taskID", taskID)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            })*/
    }

    private fun clearRadioGroupStyles(){

    }

}
package com.example.arcticapp.ui.adapters

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tele2education.R
import com.example.tele2education.data.models.education.Test
import com.example.tele2education.databinding.TestItemBinding


class TestAdapter: RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    private var dataSet = emptyList<Test>()

    class ViewHolder(private val binding: TestItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var testItem: Test

        fun bind(
            item: Test
        ) {
            testItem = item
            binding.question.text = item.question
            for (i in 0 until item.options.size) {
                val radiobutton = RadioButton(binding.root.context)
                radiobutton.text = item.options[i]
                radiobutton.id = i
                val params = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                )
                radiobutton.setTypeface(null, Typeface.BOLD)
                radiobutton.background = null
                radiobutton.setButtonDrawable(R.drawable.radiobutton_style)
                radiobutton.buttonTintList = ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_checked, android.R.attr.state_pressed),
                        intArrayOf(android.R.attr.state_pressed),
                        intArrayOf(android.R.attr.state_checked),
                        intArrayOf()
                    ),
                    intArrayOf(
                        ContextCompat.getColor(binding.root.context, R.color.darkGray),
                        ContextCompat.getColor(binding.root.context, R.color.interactable),
                        ContextCompat.getColor(binding.root.context, R.color.interactable),
                        ContextCompat.getColor(binding.root.context, R.color.darkGray)
                    )
                )
                binding.options.addView(radiobutton, params)
            }
        }

        fun checkTask(): Boolean {
            val checkedAnswerId = binding.options.indexOfChild(
                binding.options.findViewById(binding.options.checkedRadioButtonId))
            val checkedCorrectly = checkedAnswerId == testItem.rightAnswer
            for (i in 0 until binding.options.childCount) {
                binding.options.getChildAt(i).isClickable = false
            }
            (binding.options.getChildAt(checkedAnswerId) as RadioButton)
                .setTextColor(ContextCompat.getColor(binding.root.context, R.color.red))
            (binding.options.getChildAt(checkedAnswerId) as RadioButton)
                .buttonTintList = ColorStateList(
                arrayOf(
                    intArrayOf()
                ),
                intArrayOf(
                    ContextCompat.getColor(binding.root.context, R.color.red)
                )
            )

            (binding.options.getChildAt(testItem.rightAnswer) as RadioButton)
                .setTextColor(ContextCompat.getColor(binding.root.context, R.color.green))
            (binding.options.getChildAt(testItem.rightAnswer) as RadioButton)
                .buttonTintList = ColorStateList(
                arrayOf(
                    intArrayOf()
                ),
                intArrayOf(
                    ContextCompat.getColor(binding.root.context, R.color.green)
                )
            )
            return checkedCorrectly
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            TestItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = dataSet.size

    fun setDataset(tasks: ArrayList<Test>) {
        dataSet = tasks
        notifyDataSetChanged()
    }
}
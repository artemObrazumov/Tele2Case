package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tele2education.data.models.education.EducationLesson
import com.example.tele2education.databinding.EducationLessonItemBinding

class EducationLessonRecyclerAdapter(private val clickedItem: (item: EducationLesson) -> Unit): RecyclerView.Adapter<EducationLessonRecyclerAdapter.ViewHolder>(){

    private var dataList = emptyList<EducationLesson>()

    @SuppressLint("NotifyDataSetChanged")
    internal fun setDataList(dataList: ArrayList<EducationLesson>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: EducationLessonItemBinding, private val clickedItem: (model:EducationLesson) -> Unit):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(educationItem: EducationLesson) {
            binding.tvLessonName.text = educationItem.name
            Glide.with(binding.root).load(educationItem.srcImg).into(binding.imageView3)
            binding.root.setOnClickListener{clickedItem(educationItem)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationLessonRecyclerAdapter.ViewHolder =
        EducationLessonRecyclerAdapter.ViewHolder(
            EducationLessonItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false), clickedItem
        )

    override fun onBindViewHolder(holder: EducationLessonRecyclerAdapter.ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}
package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tele2education.data.models.education.Education
import com.example.tele2education.databinding.EducationItemBinding

class EducationRecyclerAdapter(private val clickedItem: (item: Education) -> Unit):
    RecyclerView.Adapter<EducationRecyclerAdapter.ViewHolder>(){

    private var dataList = emptyList<Education>()

    @SuppressLint("NotifyDataSetChanged")
    internal fun setDataList(dataList: ArrayList<Education>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: EducationItemBinding, private val clickedItem: (model: Education) -> Unit):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(educationItem: Education) {
            binding.tvName.text = educationItem.name
            Glide.with(binding.root).load(educationItem.srcImg).into(binding.imageView2)
            binding.root.setOnClickListener{clickedItem(educationItem)}
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            EducationItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false), clickedItem
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
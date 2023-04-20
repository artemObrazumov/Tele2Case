package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tele2education.R
import com.example.tele2education.data.models.Message
import com.example.tele2education.data.models.QuizParticipant
import com.example.tele2education.data.models.QuizRoom
import com.example.tele2education.databinding.GameItemBinding
import com.example.tele2education.databinding.MessageItemBinding
import com.example.tele2education.databinding.UserItemBinding
import com.example.tele2education.ui.adapter.models.Participant

class ChatAdapter(
    private var messages: List<Message>
): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    class ViewHolder(private val binding: MessageItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(message: Message) {
            binding.username.text = message.authorName
            binding.content.text = message.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MessageItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(messages: List<Message>) {
        this.messages = messages
        notifyDataSetChanged()
    }

    fun addMessage(message: Message) {
        messages = messages.toMutableList().apply { add(message) }.toList()
        notifyItemInserted(itemCount)
    }
}
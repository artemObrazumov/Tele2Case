package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tele2education.App
import com.example.tele2education.R
import com.example.tele2education.data.models.QuizParticipant
import com.example.tele2education.databinding.UserItemBinding
import com.example.tele2education.databinding.UserScoreItemBinding
import com.example.tele2education.ui.adapter.models.Participant
import com.example.tele2education.ui.adapter.models.ParticipantScore

class UsersFinishedAdapter(
    private var participants: List<ParticipantScore>,
): RecyclerView.Adapter<UsersFinishedAdapter.ViewHolder>() {
    class ViewHolder(private val binding: UserScoreItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(participant: ParticipantScore) {
            binding.userName.text = participant.participant.nickname
            binding.userScore.text = participant.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserScoreItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val participant = participants[position]
        holder.bind(participant)
    }

    override fun getItemCount() = participants.toSet().size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(participants: List<ParticipantScore>) {
        this.participants = participants.toSet().toList()
        notifyDataSetChanged()
    }
}
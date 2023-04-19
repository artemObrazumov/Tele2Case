package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tele2education.R
import com.example.tele2education.data.models.QuizParticipant
import com.example.tele2education.databinding.UserItemBinding
import com.example.tele2education.ui.adapter.models.Participant

class ParticipantsAdapter(
    private var participants: List<Participant>,
    private var adminId: String,
    private val onUserRemove:(id: String) -> Unit
): RecyclerView.Adapter<ParticipantsAdapter.ViewHolder>() {
    class ViewHolder(private val binding: UserItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(participant: Participant, isAdmin: Boolean,
            onUserRemove: (id: String) -> Unit) {
            binding.cross.setOnClickListener {
                onUserRemove(participant.userData.id)
            }
            if (!isAdmin) binding.crown.visibility = View.GONE
            if (participant.state == QuizParticipant.STATE_READY) {
                binding.userName
                    .setTextColor(binding.root.context.resources.getColor(R.color.green))
            } else {
                binding.userName
                    .setTextColor(binding.root.context.resources.getColor(R.color.red))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val participant = participants[position]
        holder.bind(participant, participant.userData.id == adminId) { userId ->
            onUserRemove(userId)
        }
    }

    override fun getItemCount() = participants.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(participants: List<Participant>) {
        this.participants = participants
        notifyDataSetChanged()
    }

    fun addParticipant(participant: Participant) {
        participants = participants.toMutableList().apply { add(participant) }.toList()
        notifyItemInserted(participants.size-1)
    }

    fun removePlayer(id: String) {
        val participant = participants.find { it.userData.id == id }
        val participantIndex = participants.indexOf(participant)
        participants = participants.toMutableList().apply { remove(participant) }.toList()
        notifyItemRemoved(participantIndex)
    }
}
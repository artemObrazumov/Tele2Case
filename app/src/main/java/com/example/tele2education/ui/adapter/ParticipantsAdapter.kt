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
import com.example.tele2education.ui.adapter.models.Participant

class ParticipantsAdapter(
    private var participants: List<Participant>,
    private var adminId: String,
    private val onUserRemove:(index: Int) -> Unit
): RecyclerView.Adapter<ParticipantsAdapter.ViewHolder>() {
    class ViewHolder(private val binding: UserItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(index: Int,
                 participant: Participant, isAdmin: Boolean,
                 canBeRemoved: Boolean,
                 onUserRemove: (index: Int) -> Unit) {
            binding.userName.text = participant.userData.nickname
            if (!canBeRemoved) binding.cross.visibility = View.GONE
            if (!isAdmin) binding.crown.visibility = View.GONE
            if (participant.state == QuizParticipant.STATE_READY) {
                binding.userName
                    .setTextColor(binding.root.context.resources.getColor(R.color.green))
            }
            binding.cross.setOnClickListener {
                onUserRemove(index)
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
        holder.bind(position, participant, participant.userData.id == adminId,
            (participant.userData.id != adminId && adminId == App.api.getCurrentUser()!!.uid),
            ) { userIndex ->
            onUserRemove(userIndex)
        }
    }

    override fun getItemCount() = participants.toSet().size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(participants: List<Participant>) {
        this.participants = participants.toSet().toList()
        notifyDataSetChanged()
    }

    fun addParticipant(participant: Participant) {
        if (participant in participants) return
        participants = participants.toMutableList().apply { add(participant) }.toSet().toList()
        val participantIndex = participants
            .indexOf(participants.find { it.userData.id == participant.userData.id })
        notifyItemInserted(participantIndex)
    }

    fun removePlayer(id: String) {
        val participant = participants.find { it.userData.id == id }
        val participantIndex = participants.indexOf(participant)
        participants = participants.toMutableList().apply { remove(participant) }.toList()
        notifyItemRemoved(participantIndex)
    }
}
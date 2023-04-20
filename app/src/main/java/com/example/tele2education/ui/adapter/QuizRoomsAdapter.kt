package com.example.tele2education.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tele2education.R
import com.example.tele2education.data.models.QuizParticipant
import com.example.tele2education.data.models.QuizRoom
import com.example.tele2education.databinding.GameItemBinding
import com.example.tele2education.databinding.UserItemBinding
import com.example.tele2education.ui.adapter.models.Participant

class QuizRoomsAdapter(
    private var rooms: List<QuizRoom>,
    private val onRoomClicked:(id: String, admin: String) -> Unit
): RecyclerView.Adapter<QuizRoomsAdapter.ViewHolder>() {
    class ViewHolder(private val binding: GameItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(room: QuizRoom,
                 onRoomClicked: (id: String, admin: String) -> Unit) {
            binding.root.setOnClickListener {
                onRoomClicked(room.id, room.adminId)
            }
            binding.participantsCounter.text = "Участников: ${room.participants.toSet().size}/4"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            GameItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room = rooms[position]
        holder.bind(room) { id, admin ->
            onRoomClicked(id, admin)
        }
    }

    override fun getItemCount() = rooms.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(rooms: List<QuizRoom>) {
        this.rooms = rooms
        notifyDataSetChanged()
    }
}
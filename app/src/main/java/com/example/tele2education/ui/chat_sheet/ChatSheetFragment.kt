package com.example.tele2education.ui.chat_sheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tele2education.App
import com.example.tele2education.data.models.Message
import com.example.tele2education.data.models.User
import com.example.tele2education.databinding.FragmentChatSheetBinding
import com.example.tele2education.ui.adapter.ChatAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class ChatSheetFragment(
    private val roomId: String
): BottomSheetDialogFragment() {
    private lateinit var binding: FragmentChatSheetBinding
    private lateinit var viewModel: ChatSheetViewModel
    private lateinit var adapter: ChatAdapter
    private lateinit var userData: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = false
        binding = FragmentChatSheetBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[ChatSheetViewModel::class.java]
        adapter = ChatAdapter(emptyList())
        binding.messagesList.layoutManager = LinearLayoutManager(requireContext())
        binding.messagesList.adapter = adapter
        viewModel.userData.observe(viewLifecycleOwner) {
            userData = it
        }
        viewModel.loadUserData()
        viewModel.chat.observe(viewLifecycleOwner) {
            adapter.setDataset(it)
            binding.messagesList.scrollToPosition(adapter.itemCount-1)
        }
        viewModel.loadChat(roomId)
        viewModel.attachToUpdates(roomId) {
            adapter.addMessage(it)
            binding.messagesList.scrollToPosition(adapter.itemCount-1)
        }
        binding.sendButton.setOnClickListener {
            val message = binding.messageInput.text.toString().trim()
            if (message.isEmpty()) {
                Snackbar
                    .make(binding.root, "Введите текст сообщения", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.uploadMessage(Message(
                "", roomId, App.api.getCurrentUser()!!.uid, message, userData.nickname
            ))
            binding.messageInput.setText("")
        }
        binding.dismiss.setOnClickListener {
            dismissNow()
        }
        return binding.root
    }
}
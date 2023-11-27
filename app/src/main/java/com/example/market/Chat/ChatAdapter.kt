package com.example.market.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.market.Chat.Chat
import com.example.market.Chat.ChatFragment
import com.example.market.Chat.ChatViewHolder
import com.example.market.R

class ChatAdapter(
    private val context: ChatFragment,
    private var chatList: List<Chat>,
) : RecyclerView.Adapter<ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]
        Glide.with(context).load(chat.purchase)
        holder.purchaseTextView.text = chat.purchase
        holder.messageTextView.text = chat.message
    }

    override fun getItemCount() = chatList.size

    fun setChats(chats: List<Chat>) {
        chatList = chats
        notifyDataSetChanged()
    }
}
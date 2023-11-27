package com.example.market.Chat

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.market.R

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val purchaseTextView: TextView = itemView.findViewById(R.id.purchaseTextView)
    val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)

    fun bind(chat: Chat, context: Context, navController: NavController) {
        Glide.with(context).load(chat.purchase)

        purchaseTextView.text = chat.purchase
        messageTextView.text = chat.message

        itemView.setOnClickListener {
            navController.navigate(R.id.chatFragment)
        }
    }
}

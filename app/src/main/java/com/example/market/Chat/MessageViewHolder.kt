package com.example.market.Chat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.market.R

class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val userTextView: TextView = itemView.findViewById(R.id.purchaseTextView)
    val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
}

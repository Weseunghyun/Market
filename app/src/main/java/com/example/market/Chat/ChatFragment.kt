package com.example.market.Chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market.Home.ChatAdapter
import com.example.market.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var chatView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        chatView = view.findViewById(R.id.messageView)
        chatAdapter = ChatAdapter(this, emptyList())

        chatView.layoutManager = LinearLayoutManager(context)
        chatView.adapter = chatAdapter

        loadMessage()
        return view
    }

    private fun loadMessage() {
        //현재 로그인된 사용자의 이메일을 가져온다
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
        if (currentUserEmail != null) {
            //db의 chat 콜렉션의 field에서 seller에 해당하는 이메일이 현재 로그인된 사용자의 이메일과 같다면 message 가져온다
            db.collection("chat")
                .whereEqualTo(
                    "seller",
                    currentUserEmail
                )
                .get()
                .addOnSuccessListener { documents ->
                    val chats = mutableListOf<Chat>()
                    //chat 컬렉션에 있는 message 모두 & db의 chat 콜렉션의 field에서 seller에 해당하는 이메일이 현재 로그인된 사용자의 이메일과 같다면 message 출력
                    for (document in documents) { //모두 출력
                        val chat = document.toObject(Chat::class.java)
                        chats.add(chat)
                    }
                    chatAdapter.setChats(chats)
                }
                .addOnFailureListener { e ->
                    Log.e("ChatFragment", "Error retrieving messages", e)
                }
        }
    }
}
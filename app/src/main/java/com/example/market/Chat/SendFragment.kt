package com.example.market.Chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.market.BottomNavigationActivity
import com.example.market.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SendFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_send, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val recipientInput = view.findViewById<EditText>(R.id.recipientInput)
        val messageInput = view.findViewById<EditText>(R.id.messageInput)
        val sendButton = view.findViewById<Button>(R.id.sendButton)

        val sellerEmail = arguments?.getString("sellerEmail")
        recipientInput.setText(sellerEmail)

        val userEmail = auth.currentUser?.email
        if (userEmail == null) {
            return view
        }

        sendButton.setOnClickListener {
            val recipient = recipientInput.text.toString()
            val message = messageInput.text.toString()

            db.collection("users").document(userEmail)
                .get()
                .addOnSuccessListener { document ->
                    val purchase: String? = document.getString("name")

                    purchase?.let {
                        val newMessage = hashMapOf(
                            "purchase" to it,
                            "message" to message,
                            "seller" to recipient
                        )

                        if (it.isEmpty() || message.isEmpty() || recipient.isEmpty()) {
                            Toast.makeText(context, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                            return@addOnSuccessListener
                        }

                        db.collection("chat")
                            .add(newMessage)
                            .addOnSuccessListener {
                                val intent = Intent(context, BottomNavigationActivity::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(
                                    context,
                                    "Failed to send message: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    } ?: run {

                        Toast.makeText(context, "판매자 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "사용자 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
        }

        return view
    }
}


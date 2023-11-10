package com.example.market

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PostFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val titleEditText = view.findViewById<EditText>(R.id.titleEditText)
        val contentEditText = view.findViewById<EditText>(R.id.contentEditText)
        val priceEditText = view.findViewById<EditText>(R.id.priceEditText)
        val postButton = view.findViewById<Button>(R.id.postButton)

        postButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()
            val priceString = priceEditText.text.toString()
            val seller = auth.currentUser?.email
            val isAvailable = true

            val price = priceString.toInt()

            // seller가 null인지 검사
            if (seller == null) {
                Toast.makeText(context, "판매자 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val post = hashMapOf(
                "title" to title,
                "content" to content,
                "price" to price,
                "seller" to seller,
                "isAvailable" to isAvailable
            )

            // 비어 있는 항목 체크
            if (title.isEmpty() || content.isEmpty() || priceString.isEmpty()) {
                Toast.makeText(context, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            db.collection("posts")
                .add(post)
                .addOnSuccessListener {
                    // 글 등록 성공, HomeFragment로 이동
                    Log.d("PostFragment", "글 등록 성공")
                    val intent = Intent(context, BottomNavigationActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    // 글 등록 실패, 오류 메시지 표시
                    Toast.makeText(context, "글 등록 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        return view
    }
}

package com.example.market.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.market.ProductDetail
import com.example.market.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class EditFragment : Fragment() {
    // Firestore 데이터베이스를 가져옵니다.
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_post, container, false)

        // Fragment의 argument에서 productId를 가져오기
        val productId = arguments?.getString("productId")
        db = FirebaseFirestore.getInstance()

        // productId가 null이 아닌 경우
        if (productId != null) {
            //view 요소를 가져오기
            val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
            val titleEditText = view.findViewById<EditText>(R.id.titleEditText)
            val contentTextView = view.findViewById<TextView>(R.id.contentTextView)
            val contentEditText = view.findViewById<EditText>(R.id.contentEditText)
            val priceTextView = view.findViewById<TextView>(R.id.priceTextView)
            val priceEditText = view.findViewById<EditText>(R.id.priceEditText)
            val sellerTextView = view.findViewById<TextView>(R.id.sellerTextView)
            val isAvailableCheckBox = view.findViewById<CheckBox>(R.id.isAvailableCheckBox)
            val editButton = view.findViewById<Button>(R.id.editButton)

            // Firestore에서 productId 가져오기
            db.collection("posts")
                .document(productId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val product = documentSnapshot.toObject(ProductDetail::class.java)
                    if (product != null) {
                        // 각 view 요소에 product의 정보를 대입
                        titleTextView.text = product.title
                        contentTextView.text = product.content
                        priceTextView.text = product.price.toString()
                        sellerTextView.text = product.seller
                        isAvailableCheckBox.isChecked = product.sell

                        editButton.setOnClickListener {
                            // 수정하기 클릭 시 editText에 현재 정보를 대입후, textView 숨기기
                            if (editButton.text == "수정하기") {
                                titleEditText.setText(titleTextView.text.toString())
                                contentEditText.setText(contentTextView.text.toString())
                                priceEditText.setText(priceTextView.text.toString())

                                titleEditText.visibility = View.VISIBLE
                                contentEditText.visibility = View.VISIBLE
                                priceEditText.visibility = View.VISIBLE
                                isAvailableCheckBox.visibility = View.VISIBLE
                                sellerTextView.visibility = View.VISIBLE

                                titleTextView.visibility = View.GONE
                                contentTextView.visibility = View.GONE
                                priceTextView.visibility = View.GONE

                                editButton.text = "수정완료"

                            } else if (editButton.text == "수정완료") {
                                // editText에서 변경한 정보를 가져오기
                                val title = titleEditText.text.toString()
                                val content = contentEditText.text.toString()
                                val priceString = priceEditText.text.toString()
                                val isAvailable = isAvailableCheckBox.isChecked
                                val price = priceString.toIntOrNull()

                                if (title.isEmpty() || content.isEmpty() || price == null) {
                                    Toast.makeText(context, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT)
                                        .show()
                                    return@setOnClickListener
                                }
                                // 현재 로그인한 사용자의 이메일을 가져오기
                                val sellerEmail = FirebaseAuth.getInstance().currentUser?.email
                                // 변경한 정보 post에 전달
                                val post = hashMapOf(
                                    "id" to productId,
                                    "title" to title,
                                    "content" to content,
                                    "price" to price,
                                    "sell" to isAvailable,
                                    "sellerEmail" to sellerEmail
                                )
                                //post 업데이트
                                db.collection("posts").document(productId)
                                    .update(post as Map<String, Any>)
                                    .addOnSuccessListener {
                                        //editText를 숨기고, textView를 보이게 하기
                                        Toast.makeText(context, "게시물이 수정되었습니다.", Toast.LENGTH_SHORT)
                                            .show()

                                        titleEditText.visibility = View.GONE
                                        contentEditText.visibility = View.GONE
                                        priceEditText.visibility = View.GONE
                                        isAvailableCheckBox.visibility = View.GONE

                                        titleTextView.visibility = View.VISIBLE
                                        contentTextView.visibility = View.VISIBLE
                                        priceTextView.visibility = View.VISIBLE

                                        titleTextView.text = title
                                        contentTextView.text = content
                                        priceTextView.text = price.toString()

                                        editButton.text = "수정하기"
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            context,
                                            "게시물 수정 실패: ${it.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                    }
                }
        }
        return view
    }
}
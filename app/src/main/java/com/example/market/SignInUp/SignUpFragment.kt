package com.example.market.SignInUp

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

class SignUpFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val emailEditText = view.findViewById<EditText>(R.id.SignUpEmailEditText)
        val passwordEditText = view.findViewById<EditText>(R.id.SignUpPasswordEditText)
        val confirmPasswordEditText = view.findViewById<EditText>(R.id.confirmPasswordEditText)
        val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
        val birthEditText = view.findViewById<EditText>(R.id.birthEditText)
        val signUpButton = view.findViewById<Button>(R.id.signUpButton)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            val name = nameEditText.text.toString()
            val birth = birthEditText.text.toString()

            if (password == confirmPassword) {
                // 생년월일의 길이가 8자가 아니면 오류 메시지 표시
                if (birth.length != 8) {
                    Toast.makeText(context, "생년월일은 8자리로 입력해주세요.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // 회원가입 성공, Firestore에 사용자 정보 저장
                            val user = hashMapOf(
                                "email" to email,
                                "password" to password,
                                "name" to name,
                                "birth" to birth
                            )

                            db.collection("users")
                                .add(user)
                                .addOnSuccessListener {
                                    // 자동로그인
                                    startActivity(Intent(context, BottomNavigationActivity::class.java))
                                    activity?.finish()
                                }
                                .addOnFailureListener {
                                    // 저장 실패, 오류 메시지 표시
                                    val message = when (it.message) {
                                        "The email address is badly formatted." -> "이메일 형식이 올바르지 않습니다."
                                        "The given password is invalid. [ Password should be at least 6 characters ]" -> "비밀번호는 최소 6자 이상이어야 합니다."
                                        "The email address is already in use by another account." -> "이미 사용 중인 이메일 주소입니다."
                                        else -> "회원가입 실패: ${it.message}"
                                    }
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            // 회원가입 실패, 오류 메시지 표시
                            val message = when (task.exception?.message) {
                                "The email address is badly formatted." -> "이메일 형식이 올바르지 않습니다."
                                "The given password is invalid. [ Password should be at least 6 characters ]" -> "비밀번호는 최소 6자 이상이어야 합니다."
                                "The email address is already in use by another account." -> "이미 사용 중인 이메일 주소입니다."
                                else -> "회원가입 실패: ${task.exception?.message}"
                            }
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                // 비밀번호와 비밀번호 확인이 일치하지 않음, 오류 메시지 표시
                Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}

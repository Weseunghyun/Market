package com.example.market.SignInUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.market.R

class InitialFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_initial, container, false)

        // NavController를 가져옵니다.
        val navController = findNavController()

        val btnLogin = view.findViewById<Button>(R.id.btn_login)
        val btnSignUp = view.findViewById<Button>(R.id.btn_sign_up)

        btnLogin.setOnClickListener {
            // 로그인 화면으로 이동하는 코드
            navController.navigate(R.id.action_initialFragment_to_loginFragment)
        }

        btnSignUp.setOnClickListener {
            // 회원가입 화면으로 이동하는 코드
            navController.navigate(R.id.action_initialFragment_to_signUpFragment)
        }

        return view
    }
}

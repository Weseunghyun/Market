package com.example.market

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        auth = FirebaseAuth.getInstance()

        // 인증 상태 리스너 추가
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                // 로그인된 경우 HomeFragment를 보여줌
                navController?.navigate(R.id.homeFragment)
            } else {
                // 로그인되지 않은 경우 LoginFragment를 보여줌
                navController?.navigate(R.id.loginFragment)
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        if (::navController.isInitialized) {
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_home -> navController?.navigate(R.id.homeFragment)
                    //R.id.action_chat -> navController?.navigate(R.id.chatFragment)
                    //R.id.action_profile -> navController?.navigate(R.id.profileFragment)
                }
                true
            }

            val fab = findViewById<FloatingActionButton>(R.id.fab)
            fab.setOnClickListener {
                // 글쓰기 화면으로 이동
                navController?.navigate(R.id.postFragment)
            }

            if (auth.currentUser != null) {
                navController?.navigate(R.id.homeFragment)  // initially show home fragment
            } else {
                navController?.navigate(R.id.loginFragment)
            }
        }
    }
}

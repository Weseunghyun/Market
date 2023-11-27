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
        //액티비티의 레이아웃을 activity_bottom_navigation으로 설정
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)

        //사용자가 로그인된 상태인지를 확인하기위해 auth를 가져옴.
        auth = FirebaseAuth.getInstance()

        // 인증 상태가 변경될 때마다 동작을 수행하는 리스너 추가
        auth.addAuthStateListener { firebaseAuth ->
            //현재 로그인된 유저를 가져오는 currentUser
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
        //프래그먼트간 이동을 위해 navHostFragment를 가져온다.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        //navController가 초기화 되어있는지 확인
        if (::navController.isInitialized) {
            //바텀 내비게이션뷰의 아이템이 선택될때 동작 수행
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_home -> navController?.navigate(R.id.homeFragment)
                    R.id.action_chat -> navController?.navigate(R.id.chatFragment)
                    R.id.action_profile -> navController?.navigate(R.id.profileFragment)
                }
                true
            }

            //액티비티가 재개될 때마다 로그인 된 상태인지 확인한다.
            if (auth.currentUser != null) {
                navController?.navigate(R.id.homeFragment)  // initially show home fragment
            } else {
                navController?.navigate(R.id.loginFragment)
            }
        }
    }
}

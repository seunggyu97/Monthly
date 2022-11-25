package com.example.monthly.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.monthly.databinding.ActivitySplashBinding
import com.example.monthly.viewModel.SplashViewModel

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

    }

    private fun init() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        // Todo : Room DB에 사용자 데이터가 있는지 / 없는지 판별 후 액티비티 호출
        // Todo : 데이터 있음 -> Intent - MainActivity
        // Todo : 데이터 없음 -> Intent - InitActivity
        Intent(this, InitActivity::class.java)

        // 3초 지연
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, InitActivity::class.java))
            finish()
        }, 3000)
//        viewModel.user.observe(this) {
//            val intent: Intent by lazy {
//                // 유저 데이터 있을 때
//                if (it.userName != null) Intent(this, MainActivity::class.java)
//                // 유저 데이터 없을 때
//                else Intent(this, InitActivity::class.java)
//            }
//            startActivity(intent)
//            finish()
//        }
    }
}
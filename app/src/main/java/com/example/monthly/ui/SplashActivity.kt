package com.example.monthly.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.MainActivity
import com.example.monthly.databinding.ActivitySplashBinding
import com.example.monthly.viewModel.SplashViewModel

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var splashViewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

    }

    private fun init() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        setContentView(binding.root)

        // Todo : Room DB에 사용자 데이터가 있는지 / 없는지 판별 후 액티비티 호출
        // Todo : 데이터 있음 -> Intent - MainActivity
        // Todo : 데이터 없음 -> Intent - InitActivity
        splashViewModel.currentData.observe(this) {
            val intent: Intent by lazy {
                if (it == null) {
                    Log.e("MyTag", "User 정보 없음")
                    Intent(this, InitActivity::class.java)
                }
                // User 정보 없을 때
                else {
                    Log.e("MyTag", "User 정보 있음 User")
                    Intent(this, MainActivity::class.java)
                }
            }
            // 3초 지연
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(intent)
                finish()
            }, 3000)
        }
    }
}
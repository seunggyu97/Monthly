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

        Intent(this, InitActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, InitActivity::class.java))
            finish()
        }, 3000)
//        viewModel.user.observe(this) {
//            val intent: Intent by lazy {
//                // 냉장고 있을 때
//                if (it.refId != null) Intent(this, MainActivity::class.java)
//                // 냉장고 없을 때
//                else Intent(this, TutorialActivity::class.java)
//            }
//            startActivity(intent)
//            finish()
//        }
    }
}
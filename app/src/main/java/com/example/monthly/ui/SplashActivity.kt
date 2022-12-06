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

        splashViewModel.user.observe(this) {
            var intent = Intent(this@SplashActivity, InitActivity::class.java)
            if(it != null) {
                Log.e("MyTag","User Data is not Null")
                intent = Intent(this@SplashActivity, MainActivity::class.java)
            }else{
                Log.e("MyTag","User Data is Null")
            }
            // 3초 지연
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(intent)
                finish()
            }, 3000)
        }
    }
}
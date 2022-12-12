package com.example.monthly.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.GlobalApplication
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
//
            it?.let {
                intent = Intent(this@SplashActivity, MainActivity::class.java)
                Log.e("MyTag", "User Data is not Null")

                val password = GlobalApplication.prefs.getString("securityPassword")
                if(password != "") {
                    intent = Intent(this@SplashActivity, SettingPasswordActivity::class.java)
                    intent.putExtra("alreadyHavePassword", true)
                    intent.putExtra("appStart", true)
                }

            }
            // 3초 지연
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(intent)
                finish()
            }, 2000)
        }
    }

    override fun onResume() {
        super.onResume()

    }
}
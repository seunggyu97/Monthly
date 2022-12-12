package com.example.monthly.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.BasicActivity
import com.example.monthly.GlobalApplication
import com.example.monthly.R
import com.example.monthly.databinding.ActivitySettingBinding
import com.example.monthly.enumClass.Status
import com.example.monthly.viewModel.SettingViewModel

class SettingActivity : BasicActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var settingViewModel: SettingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        settingViewModel = ViewModelProvider(this)[SettingViewModel::class.java]

        // 앱 푸시 설정 상태 observe
        settingViewModel.pushStatus.observe(this) {
            it?.let {
                binding.tvStatusPush.text = it.toString()
            }
        }

        // 앱 잠금 설정 상태 observe
        settingViewModel.securityStatus.observe(this) {
            it?.let {
                binding.tvStatusSecurity.text = it.toString()
            }
        }

        binding.apply {
            title = getString(R.string.menu_title_setting)
            activity = this@SettingActivity
            viewModel = settingViewModel

            clSettingPush.setOnClickListener {
                startActivity(Intent(this@SettingActivity, SettingPushActivity::class.java))
            }

            clSettingSecurity.setOnClickListener {
                val pw = GlobalApplication.prefs.getString("securityPassword")
                Log.e("Mytag","저장된 비밀번호 : ${pw}")
                when(pw) {
                    "" -> {
                        // 비밀번호 설정이 안되어있으면 0을 반환함
                        Log.e("MyTag", "비번 노설정")
                        startActivity(Intent(this@SettingActivity, SettingSecurityActivity::class.java))
                    }
                    else -> {
                        // 설정이 되어있는 경우
                        Log.e("MyTag", "비번 설정")

                        intent = Intent(this@SettingActivity, SettingPasswordActivity::class.java)
                        intent.putExtra("alreadyHavePassword", true)
                        startActivity(intent)
                    }
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out_right)
        }
    }

    override fun onResume() {
        super.onResume()

        settingViewModel.initialize()
    }
}

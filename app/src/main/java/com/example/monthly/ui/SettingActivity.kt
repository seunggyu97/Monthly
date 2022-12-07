package com.example.monthly.ui

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.BasicActivity
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
        settingViewModel = ViewModelProvider(this).get(SettingViewModel::class.java)

        // 앱 푸시 설정 상태 observe
        settingViewModel.pushStatus.observe(this) {
            it?.let {

                binding.tvStatusPush.text = it.toString()
                binding.tvStatusPush.text = it.toString()
            }
        }

        // 앱 잠금 설정 상태 observe
        settingViewModel.securityStatus.observe(this) {
            it?.let {
                binding.tvStatusSecurity.text = it.toString()
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
        }

    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out_right)
        }
    }
}

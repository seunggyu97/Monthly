package com.example.monthly.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.BasicActivity
import com.example.monthly.GlobalApplication
import com.example.monthly.R
import com.example.monthly.databinding.ActivitySettingPushBinding
import com.example.monthly.enumClass.Status
import com.example.monthly.viewModel.SettingPushViewModel
import com.github.angads25.toggle.interfaces.OnToggledListener
import com.github.angads25.toggle.model.ToggleableView


class SettingPushActivity : BasicActivity() {
    private lateinit var binding: ActivitySettingPushBinding
    private lateinit var settingPushViewModel: SettingPushViewModel

    private var isLableOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_push)
        settingPushViewModel = ViewModelProvider(this).get(SettingPushViewModel::class.java)

        // 앱 푸시 설정 상태 observe
        settingPushViewModel.pushStatus.observe(this) {
            it?.let {
                if (it == Status.ON) {
                    // 푸시알림 설정한 상태
                    Log.e("MyTag","푸시알림 설정한 상태")
                    setLabelOn()
                } else {
                    // 설정하지 않은 상태
                    Log.e("MyTag","푸시알림 설정하지 않은 상태")
                    setLabelOff()
                }
            }
        }

        // 앱 푸시 시간 observe
        settingPushViewModel.time.observe(this) {
            it?.let {
                binding.tvSettingTime.text = it.toString()
            }
        }

        binding.apply {
            title = getString(R.string.menu_title_setting_push)
            activity = this@SettingPushActivity
            viewModel = settingPushViewModel

            btnTogglePush.setOnToggledListener(object : OnToggledListener {
                override fun onSwitched(toggleableView: ToggleableView?, isOn: Boolean) {
                    if (isOn) {
                        setLabelOn()
                        settingPushViewModel.setPushStatus(true)
                    } else {
                        setLabelOff()
                        settingPushViewModel.setPushStatus(false)
                    }
                }
            })

            cvSettingPushTime.setOnClickListener {
                if(isLableOn)
                    Toast.makeText(application, "시간 클릭 설정 ON", Toast.LENGTH_SHORT).show()
            }
        }


    }

    /**
     * == 푸시 알림 ON 상태로 설정 ==
     * 토글 버튼 ON 상태로 변경
     * 알림 시간 설정 항목들 활성화
     **/
    fun setLabelOn() {
        binding.apply {
            btnTogglePush.labelOn
            tvSettingPushTime.setTextColor(getColor(R.color.font_black))
            tvEveryday.setTextColor(getColor(R.color.font_black))
            tvHour.setTextColor(getColor(R.color.font_black))
            tvSettingTime.setTextColor(getColor(R.color.font_black))
            btnTogglePush.isOn = true
            isLableOn = true
        }
    }

    /**
     * == 푸시 알림 OFF 상태로 설정 ==
     * 토글 버튼 OFF 상태로 변경
     * 알림 시간 설정 항목들 비활성화
     **/
    fun setLabelOff() {
        binding.apply {
            btnTogglePush.labelOff
            tvSettingPushTime.setTextColor(getColor(R.color.font_grey_light))
            tvEveryday.setTextColor(getColor(R.color.font_grey_light))
            tvHour.setTextColor(getColor(R.color.font_grey_light))
            tvSettingTime.setTextColor(getColor(R.color.font_grey_light))
            btnTogglePush.isOn = false
            isLableOn = false
        }
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out_right)
        }
    }
}

package com.example.monthly.ui

import BottomSheetDialog
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.BasicActivity
import com.example.monthly.Constant.Companion.NOTIFICATION_ID
import com.example.monthly.GlobalApplication
import com.example.monthly.MyReceiver
import com.example.monthly.R
import com.example.monthly.databinding.ActivitySettingPushBinding
import com.example.monthly.enumClass.Status
import com.example.monthly.ui.dialogs.InitDialogInterface
import com.example.monthly.viewModel.SettingPushViewModel
import com.github.angads25.toggle.interfaces.OnToggledListener
import com.github.angads25.toggle.model.ToggleableView


class SettingPushActivity : BasicActivity(), InitDialogInterface {
    private lateinit var binding: ActivitySettingPushBinding
    private lateinit var settingPushViewModel: SettingPushViewModel

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    private var isLableOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_push)
        settingPushViewModel = ViewModelProvider(this).get(SettingPushViewModel::class.java)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, MyReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            this, NOTIFICATION_ID, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

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
                        settingPushViewModel.setPushStatus(true)
                    } else {
                        settingPushViewModel.setPushStatus(false)
                    }
                }
            })

            cvSettingPushTime.setOnClickListener {
                if(isLableOn){
                    showBottomSheet()
                }
            }

            // TODO : 앱 배포시 삭제(레이아웃 포함)
            btnPushTest.setOnClickListener {
                alarmManager.set(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    (SystemClock.elapsedRealtime() + 5000),
                    pendingIntent
                ) // set : 일회성 알림

                Toast.makeText(application, "5초 후에 알림 울림", Toast.LENGTH_SHORT).show()
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

        // 알림 취소
        alarmManager.cancel(pendingIntent)
    }

    fun showBottomSheet() {
        val setTime = Integer.parseInt(binding.tvSettingTime.text.toString())
        val titleText = "알림 시간 설정"
        val bottomSheet = BottomSheetDialog(this, 0, 23, setTime, titleText)
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out_right)
        }
    }

    override fun onCompleteButtonClicked(content: String) {
        settingPushViewModel.setTime(Integer.parseInt(content))
    }

    override fun onFinishButtonClicked(bitmap: Bitmap) {
        // Nothing To Do
    }
}

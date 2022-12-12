package com.example.monthly.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.BasicActivity
import com.example.monthly.GlobalApplication
import com.example.monthly.R
import com.example.monthly.databinding.ActivitySettingSecurityBinding
import com.example.monthly.enumClass.Status
import com.example.monthly.viewModel.SettingSecurityViewModel
import com.github.angads25.toggle.interfaces.OnToggledListener
import com.github.angads25.toggle.model.ToggleableView

private var isViewShouldBeVisibled = false
private lateinit var animViewLayout: androidx.constraintlayout.widget.ConstraintLayout

class SettingSecurityActivity : BasicActivity() {
    private lateinit var binding: ActivitySettingSecurityBinding
    private lateinit var settingSecurityViewModel: SettingSecurityViewModel

    private lateinit var slideDownAnim: Animation
    private lateinit var slideUpAnim: Animation
    private var isFirstActivate = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_security)
        settingSecurityViewModel = ViewModelProvider(this).get(SettingSecurityViewModel::class.java)

        // 슬라이드 애니메이션이 완료됐는지 판단하는 리스너
        val animListener = SlidingPageAnimationListener()
        slideDownAnim = AnimationUtils.loadAnimation(this, R.anim.anim_slide_down)
        slideUpAnim = AnimationUtils.loadAnimation(this, R.anim.anim_slide_up)
        slideDownAnim.setAnimationListener(animListener);
        slideUpAnim.setAnimationListener(animListener);

        // 잠금 사용 설정 상태 observe
        settingSecurityViewModel.securityStatus.observe(this) {
            it?.let {
                if (it == Status.ON) {
                    // 잠금 사용 설정한 상태
                    Log.e("MyTag", "잠금 사용 설정한 상태")
                    setSecurityLabelOn()

                    binding.clChangePassword.visibility = View.VISIBLE
                    binding.clBiometricAuth.visibility = View.VISIBLE
                } else {
                    // 설정하지 않은 상태
                    Log.e("MyTag", "잠금 사용 설정하지 않은 상태")
                    setSecurityLabelOff()

                    // 애니메이션 리스너를 사용하기 때문에 처음 이후의 View.GONE은 따로 설정 해야함
                    // 안그러면 slideUpAnim애님이 보여지지 않은 채 View.GONE이 바로 되어버리는 현상 발생
                    if (isFirstActivate) {
                        binding.clChangePassword.visibility = View.GONE
                        binding.clBiometricAuth.visibility = View.GONE

                        isFirstActivate = false
                    }
                }
            }
        }

        // 생체인증 잠금 사용 설정 상태 observe
        settingSecurityViewModel.biometricAuthStatus.observe(this) {
            it?.let {
                if (it == Status.ON) {
                    // 잠금 사용 설정한 상태
                    Log.e("MyTag", "생체인증 잠금 사용 설정한 상태")
                    setBiometricAuthLabelOn()
                } else {
                    // 설정하지 않은 상태
                    Log.e("MyTag", "생체인증 잠금 사용 설정하지 않은 상태")
                    setBiometricAuthLabelOff()

                }
            }
        }

        binding.apply {
            title = getString(R.string.menu_title_setting_security)
            activity = this@SettingSecurityActivity
            viewModel = settingSecurityViewModel

            animViewLayout = clAnimViewLayout
            btnToggleSecurity.setOnToggledListener(object : OnToggledListener {
                override fun onSwitched(toggleableView: ToggleableView?, isOn: Boolean) {
                    if (isOn) {
                        startVisibleAnim()
                    } else {
                        isFirstActivate = false
                        startHideAnim()
                    }
                }
            })

            btnToggleBiometricAuth.setOnToggledListener(object : OnToggledListener {
                override fun onSwitched(toggleableView: ToggleableView?, isOn: Boolean) {
                    if (isOn) {
                        settingSecurityViewModel.setBiometricAuthStatus(true)
                    } else {
                        settingSecurityViewModel.setBiometricAuthStatus(false)
                    }
                }
            })

            clChangePassword.setOnClickListener {
                startActivity(Intent(application, SettingPasswordActivity::class.java))
            }
        }


    }

    /**
     * == 비밀번호 설정 ON 상태로 설정 ==
     * 토글 버튼 ON 상태로 변경
     * 이하 View 항목들 visible(비밀번호 설정 view, 생체인증 사용 설정 view)
     **/
    fun setSecurityLabelOn() {
        binding.apply {
            btnToggleSecurity.labelOn
            btnToggleSecurity.isOn = true
            if(!isFirstActivate) {
                intent = Intent(applicationContext, SettingPasswordActivity::class.java)
                intent.putExtra("firstActivate", true)
                startActivity(intent)
            }
        }
    }

    /**
     * == 푸시 알림 OFF 상태로 설정 ==
     * 토글 버튼 OFF 상태로 변경
     * 이하 View 항목들 invisible(비밀번호 설정 view, 생체인증 사용 설정 view)
     **/
    fun setSecurityLabelOff() {
        binding.apply {
            btnToggleSecurity.labelOff
            btnToggleSecurity.isOn = false
        }
    }

    /**
     * == 비밀번호 설정 ON 상태로 설정 ==
     * 토글 버튼 ON 상태로 변경
     * 이하 View 항목들 visible(비밀번호 설정 view, 생체인증 사용 설정 view)
     **/
    fun setBiometricAuthLabelOn() {
        binding.apply {
            btnToggleBiometricAuth.labelOn

        }
    }

    /**
     * == 푸시 알림 OFF 상태로 설정 ==
     * 토글 버튼 OFF 상태로 변경
     * 이하 View 항목들 invisible(비밀번호 설정 view, 생체인증 사용 설정 view)
     **/
    fun setBiometricAuthLabelOff() {
        binding.apply {
            btnToggleBiometricAuth.labelOff

        }

    }

    fun startVisibleAnim() {
        animViewLayout.visibility = View.VISIBLE
        settingSecurityViewModel.setSecurityStatus(true)

        binding.apply {
            clChangePassword.startAnimation(slideDownAnim)
            clBiometricAuth.startAnimation(slideDownAnim)
        }

        isViewShouldBeVisibled = true
    }

    fun startHideAnim() {
        settingSecurityViewModel.setSecurityStatus(false)

        binding.apply {
            clChangePassword.startAnimation(slideUpAnim)
            clBiometricAuth.startAnimation(slideUpAnim)
        }

        isViewShouldBeVisibled = false
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out_right)
        }
    }

    override fun onResume() {
        super.onResume()
        // 비번 설정 안하고 취소로 돌아왔을 때
        if(GlobalApplication.prefs.getString("securityPassword") == "") {
            setSecurityLabelOff()

            startHideAnim()

        }

    }

    private class SlidingPageAnimationListener : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {

        }

        override fun onAnimationEnd(animation: Animation) {
            if (!isViewShouldBeVisibled) {
                animViewLayout.setVisibility(View.GONE)
            }
        }

        override fun onAnimationRepeat(animation: Animation) {}
    }

}

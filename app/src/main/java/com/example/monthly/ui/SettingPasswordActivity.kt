package com.example.monthly.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.VibrationEffect.DEFAULT_AMPLITUDE
import android.os.Vibrator
import android.util.Log
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.monthly.GlobalApplication
import com.example.monthly.PasswordInputActivity
import com.example.monthly.R
import com.example.monthly.databinding.ActivitySettingPasswordBinding
import com.example.monthly.viewModel.SettingPasswordViewModel
import kotlinx.coroutines.*


class SettingPasswordActivity : PasswordInputActivity() {
    private lateinit var binding: ActivitySettingPasswordBinding
    private lateinit var settingPasswordViewModel: SettingPasswordViewModel
    private var isCheckingPassword = false // 진입 시점 구분 : 비밀번호 설정 되어있는 상태에서 화면에 진입한 경우 true, 이 경우 비밀번호를 확인하고 시작해야 함
    private var isFirstActivate = false // 진입 시점 구분 : 최초 비밀번호 설정 ON 으로 했을 경우 true
    private var isInCheckingProcess = false // 비밀번호 확인 과정 진행중인 경우 true
    private var isFromChangePassword = false // 비밀번호 변경을 통해 들어온 경우 true
    private var stringBuilder = StringBuilder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_password)
        settingPasswordViewModel = ViewModelProvider(this).get(SettingPasswordViewModel::class.java)

        isCheckingPassword = intent.getBooleanExtra("alreadyHavePassword", false)
        isFirstActivate = intent.getBooleanExtra("firstActivate", false)
        isFromChangePassword = intent.getBooleanExtra("fromChangePassword", false)

        // 입력한 비밀번호 값을 저장하는 LiveData를 observe
        settingPasswordViewModel.inputPassword.observe(this) {
            it?.let {

                when (it.length) {
                    0 -> {
                        binding.passwordInput = "―  ―  ―  ―"
                        Log.e("MyTag","0번째 숫자 입력")
                    }
                    1 -> {
                        binding.passwordInput = "●  ―  ―  ―"
                        Log.e("MyTag","1번째 숫자 입력")

                        when (isFromChangePassword) {
                            false -> {
                                binding.subTitleGuide = getString(R.string.insert_password_guide)
                            }
                            true -> {
                                binding.subTitleGuide = getString(R.string.modify_password_guide)
                            }
                        }
                    }
                    2 -> binding.passwordInput = "●  ●  ―  ―"
                    3 -> binding.passwordInput = "●  ●  ●  ―"
                    4 -> binding.passwordInput = "●  ●  ●  ●"
                }

                // 4개 숫자 모두 입력 되었을 때
                if (it.length == 4) {
                    when (isCheckingPassword) {
                        true -> {
                            checkPassword()
                            Log.e("myTag", "기존 비밀번호 일치여부 판단")
                        } // 기존 비밀번호 일치여부 판단
                        false -> {
                            Log.e("myTag", "비밀번호 확인 프로세스 시작")

                            window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            GlobalScope.launch {
                                delay(500L)
                                Dispatchers.Main {
                                    startCheckPasswordProcess() // 비밀번호 확인 프로세스 시작
                                }
                            }
                        }
                    }
                }
            }
        }

        settingPasswordViewModel.inputCheckPassword.observe(this) {
            it?.let {

                when (it.length) {
                    0 -> binding.passwordInput = "―  ―  ―  ―"
                    1 -> binding.passwordInput = "●  ―  ―  ―"
                    2 -> binding.passwordInput = "●  ●  ―  ―"
                    3 -> binding.passwordInput = "●  ●  ●  ―"
                    4 -> binding.passwordInput = "●  ●  ●  ●"
                }

                // 4개 숫자 모두 입력 되면, inputPassword와 비교 작업 수행
                if (it.length == 4) {
                    Log.e("mytag","4개 숫자 모두 입력, inputPassword 비교 작업 수행")
                    finalCheckPassword()
                }
            }
        }

        binding.apply {
            when (isFromChangePassword) {
                false -> {
                    subTitle = getString(R.string.insert_password)
                    subTitleGuide = getString(R.string.insert_password_guide)
                }
                true -> {
                    subTitle = getString(R.string.menu_title_setting_password_modify)
                    subTitleGuide = getString(R.string.modify_password_guide)
                }
            }
            activity = this@SettingPasswordActivity
            viewModel = settingPasswordViewModel

        }
        initButtonClickListener()

    }

    fun input(isInputPassword: Boolean, num: Int) {

        stringBuilder.append(num.toString())

        when(isInputPassword) {
            true -> {
                settingPasswordViewModel.setInputPassword(stringBuilder.toString())
            }
            false -> {
                settingPasswordViewModel.setInputCheckPassword(stringBuilder.toString())
            }
        }
    }

    fun startCheckPasswordProcess() {
        isInCheckingProcess = true
        stringBuilder = StringBuilder() // StringBuilder 초기화

        binding.apply {
            subTitleGuide = getString(R.string.modify_password_guide_onemore)
            settingPasswordViewModel.setInputCheckPassword("")

        }
        initButtonClickListener()
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    fun checkPassword() {
        Log.e("mytag", "저장된 비번 : ${GlobalApplication.prefs.getString("securityPassword")}")
        Log.e("mytag", "입력한 비번 : ${settingPasswordViewModel.inputPassword.value}")

        if (GlobalApplication.prefs.getString("securityPassword") == settingPasswordViewModel.inputPassword.value) {
            Log.e("mytag", "비번 일치")
            intent = Intent(applicationContext, SettingSecurityActivity::class.java)
            startActivity(intent)
            FinishWithAnim()
        } else {
            Log.e("mytag", "비번 일치하지 않음")

            YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(0)
                .playOn(binding.tvPasswordInput)

            var vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            if (Build.VERSION.SDK_INT >= 26) vibrator.vibrate(
                VibrationEffect.createOneShot(
                    700,
                    DEFAULT_AMPLITUDE
                )
            )
            else vibrator.vibrate(700)
            settingPasswordViewModel.clearInputPassword()
            binding.tvSettingPasswordSubtitleGuide.text = getString(R.string.modify_password_guide_error)
            initButtonClickListener()
        }
    }

    fun finalCheckPassword() {
        if (settingPasswordViewModel.inputPassword.value.equals(settingPasswordViewModel.inputCheckPassword.value)) {
            // 비밀번호 일치하면
            GlobalApplication.prefs.setString(
                "securityPassword",
                settingPasswordViewModel.inputPassword.value.toString()
            )
            FinishWithAnim()
        } else {
            // 일치하지 않으면
            binding.tvSettingPasswordSubtitleGuide.text =
                getString(R.string.modify_password_guide_error)
            isInCheckingProcess = false
            settingPasswordViewModel.clearInputPassword()
            settingPasswordViewModel.clearInputCheckPassword()

            YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(0)
                .playOn(binding.tvPasswordInput)

            var vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            if (Build.VERSION.SDK_INT >= 26) vibrator.vibrate(
                VibrationEffect.createOneShot(
                    700,
                    DEFAULT_AMPLITUDE
                )
            )

            else vibrator.vibrate(700)
            initButtonClickListener()
        }
    }

    fun initButtonClickListener() {
        stringBuilder = StringBuilder() // StringBuilder 초기화

        binding.apply {
            tlClose.setOnClickListener { FinishWithAnim() }

            // 비밀번호 확인 프로세스 진행중이 아닐 때
            if (!isInCheckingProcess) {
                tl1.setOnClickListener { input(true, 1) }
                tl2.setOnClickListener { input(true, 2) }
                tl3.setOnClickListener { input(true, 3) }
                tl4.setOnClickListener { input(true, 4) }
                tl5.setOnClickListener { input(true, 5) }
                tl6.setOnClickListener { input(true, 6) }
                tl7.setOnClickListener { input(true, 7) }
                tl8.setOnClickListener { input(true, 8) }
                tl9.setOnClickListener { input(true, 9) }
                tl0.setOnClickListener { input(true, 0) }
                tlBack.setOnClickListener {
                    if (stringBuilder.isNotEmpty()) {
                        Log.e("MyTag", "${stringBuilder.toString().length}번째 숫자 삭제")

                        stringBuilder.deleteCharAt(
                            stringBuilder.toString().length - 1
                        )
                        settingPasswordViewModel.setInputPassword(stringBuilder.toString())
                    }
                }
            } else {
                // 비밀번호 확인 프로세스 진행중일 때
                tl1.setOnClickListener { input(false, 1) }
                tl2.setOnClickListener { input(false, 2) }
                tl3.setOnClickListener { input(false, 3) }
                tl4.setOnClickListener { input(false, 4) }
                tl5.setOnClickListener { input(false, 5) }
                tl6.setOnClickListener { input(false, 6) }
                tl7.setOnClickListener { input(false, 7) }
                tl8.setOnClickListener { input(false, 8) }
                tl9.setOnClickListener { input(false, 9) }
                tl0.setOnClickListener { input(false, 0) }
                tlBack.setOnClickListener {
                    if (stringBuilder.isNotEmpty()) {
                        stringBuilder.deleteCharAt(
                            stringBuilder.toString().length - 1
                        )
                        settingPasswordViewModel.setInputCheckPassword(stringBuilder.toString())
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out_down)
        }
    }

}

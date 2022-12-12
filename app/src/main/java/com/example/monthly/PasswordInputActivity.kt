package com.example.monthly

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

open class PasswordInputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 이 화면은 아래에서 위로 슬라이딩 하면서 켜짐
        overridePendingTransition(R.anim.anim_slide_in_up, R.anim.anim_none)
    }

    protected fun FinishWithAnim() {
        finish()
        if (isFinishing) {
            // 이 화면은 위에서 아래로 슬라이딩 하면서 사라짐
            overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out_down)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (isFinishing) {
            // back 버튼으로 화면 종료가 야기되면 동작함.
            overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out_down)
        }
    }
}
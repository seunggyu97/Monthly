package com.example.monthly

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity

open class BasicActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 이 화면은 왼쪽에서 오른쪽으로 슬라이딩 하면서 켜짐
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_none)
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    }

    protected fun FinishWithAnim() {
        finish()
        if (isFinishing) {
            // 이 화면은 왼쪽에서 오른쪽으로 슬라이딩 하면서 사라잠
            overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out_right)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isFinishing) {
            // back 버튼으로 화면 종료가 야기되면 동작함.
            overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out_right)
        }
    }
}
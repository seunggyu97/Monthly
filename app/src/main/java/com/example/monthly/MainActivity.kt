package com.example.monthly

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.databinding.ActivityMainBinding
import com.example.monthly.databinding.ActivitySettingBinding
import com.example.monthly.ui.SettingActivity
import com.example.monthly.util.AppendCommaToPriceValue
import com.example.monthly.viewModel.MainViewModel


//import com.example.monthly.ui.theme.MonthlyTheme

class MainActivity : BasicActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.user.observe(this) {
            it?.let {
                val userName = it.name + "님"
                val limitValue = AppendCommaToPriceValue(it.limit) + "원"
                binding.apply {
                    viewNav.tvNaviName.text = userName
                    viewNav.tvNaviLimitValue.text = limitValue
                }
            }
        }

        binding.apply {
            btnMenu.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.END) // trigger되면 NavigationView Open
            }

            viewNav.ibClose.setOnClickListener {
                drawerLayout.closeDrawer(GravityCompat.END)
            }

            viewNav.ibSetting.setOnClickListener {
                Log.e("MyTag", "ibsetting clicked")
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
            }
        }

        // 앱 status bar 투명하게 설정
        makeStatusBarTransParent()
    }

    // 상태바를 투명하게 만드는 작업
    fun Activity.makeStatusBarTransParent() {
        window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }
    }

}

package com.example.monthly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.databinding.ActivityMainBinding
//import com.example.monthly.databinding.MainIncludeDrawerBinding
import com.example.monthly.viewModel.MainViewModel

//import com.example.monthly.ui.theme.MonthlyTheme

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
//    private lateinit var drawerBinding: MainIncludeDrawerBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        drawerBinding = DataBindingUtil.setContentView(this, R.layout.main_include_drawer)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.apply {
            btnMenu.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.END) // trigger되면 NavigationView Open
            }
        }
    }
}

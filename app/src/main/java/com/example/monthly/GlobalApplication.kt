package com.example.monthly

import android.app.Application
import com.example.monthly.util.MySharedPreference

class GlobalApplication : Application() {
    companion object {
        lateinit var prefs: MySharedPreference
    }

    override fun onCreate() {
        super.onCreate()
        prefs = MySharedPreference(applicationContext)

        // 다른 초기화 코드들

    }
}
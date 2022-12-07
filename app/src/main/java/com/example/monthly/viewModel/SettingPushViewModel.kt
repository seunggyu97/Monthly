package com.example.monthly.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.monthly.GlobalApplication
import com.example.monthly.data.dataclass.User
import com.example.monthly.data.db.UserDatabase
import com.example.monthly.data.repository.UserRepository
import com.example.monthly.enumClass.Status

class SettingPushViewModel(application: Application) : AndroidViewModel(application) {
    private val _pushStatus = MutableLiveData<Status>()
    private val _time = MutableLiveData<Int>()

    val pushStatus: LiveData<Status>
        get() = _pushStatus
    val time: LiveData<Int>
        get() = _time

    init {
        if(GlobalApplication.prefs.getBoolean("pushSetting")) {
            _pushStatus.value = Status.ON
        }
        else _pushStatus.value = Status.OFF

        _time.value = GlobalApplication.prefs.getInt("pushTime")
    }

}
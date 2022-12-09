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

class SettingViewModel(application: Application) : AndroidViewModel(application) {
    private val _securityStatus = MutableLiveData<Status>()
    private val _pushStatus = MutableLiveData<Status>()

    val securityStatus: LiveData<Status>
        get() = _securityStatus
    val pushStatus: LiveData<Status>
        get() = _pushStatus

    init {
        initialize()
    }

    fun initialize() {
        if(GlobalApplication.prefs.getBoolean("securitySetting")) {
            _securityStatus.value = Status.ON
        }
        else _securityStatus.value = Status.OFF

        if(GlobalApplication.prefs.getBoolean("pushSetting")) _pushStatus.value = Status.ON
        else _pushStatus.value = Status.OFF
    }
}
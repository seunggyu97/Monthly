package com.example.monthly.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.monthly.GlobalApplication
import com.example.monthly.enumClass.Status

class SettingSecurityViewModel(application: Application) : AndroidViewModel(application) {
    private val _securityStatus = MutableLiveData<Status>()
    private val _biometricAuthStatus = MutableLiveData<Status>()

    val securityStatus: LiveData<Status>
        get() = _securityStatus
    val biometricAuthStatus: LiveData<Status>
        get() = _biometricAuthStatus

    init {
        if (!GlobalApplication.prefs.getString("securityPassword").equals("")) {
            _securityStatus.value = Status.ON
        } else _securityStatus.value = Status.OFF

        if (GlobalApplication.prefs.getBoolean("biometricAuthSetting")) {
            _biometricAuthStatus.value = Status.ON
        } else _biometricAuthStatus.value = Status.OFF
    }

    fun setSecurityStatus(status: Boolean) {
        when (status) {
            true -> {
                _securityStatus.value = Status.ON
            }
            false -> {
                _securityStatus.value = Status.OFF
                GlobalApplication.prefs.setString("securityPassword", "")
            }
        }
    }

    fun setBiometricAuthStatus(status: Boolean) {
        when (status) {
            true -> {
                _biometricAuthStatus.value = Status.ON
                GlobalApplication.prefs.setBoolean("biometricAuthSetting", true)
            }
            false -> {
                _biometricAuthStatus.value = Status.OFF
                GlobalApplication.prefs.setBoolean("biometricAuthSetting", false)
            }
        }
    }

}
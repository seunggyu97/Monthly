package com.example.monthly.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.monthly.data.dataclass.User
import com.example.monthly.data.db.UserDatabase
import com.example.monthly.data.repository.UserRepository
import com.example.monthly.enumClass.Status

class SettingSecurityViewModel(application: Application) : AndroidViewModel(application) {
    private val _securityStatus = MutableLiveData<Status>()

    val securityStatus: LiveData<Status>
        get() = _securityStatus

    init {
        _securityStatus.value = Status.OFF
    }

}
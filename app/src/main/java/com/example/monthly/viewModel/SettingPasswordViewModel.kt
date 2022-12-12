package com.example.monthly.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SettingPasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val _inputPassword = MutableLiveData<String>()
    private val _inputCheckPassword = MutableLiveData<String>()

    val inputPassword: LiveData<String>
        get() = _inputPassword
    val inputCheckPassword: LiveData<String>
        get() = _inputCheckPassword

    init {
        _inputPassword.value = ""
    }

    fun setInputPassword(input: String) {
        _inputPassword.value = input
    }

    fun setInputCheckPassword(input: String) {
        _inputCheckPassword.value = input
    }

    fun clearInputPassword() {
        _inputPassword.value = ""
    }
    fun clearInputCheckPassword() {
        _inputCheckPassword.value = ""
    }
}
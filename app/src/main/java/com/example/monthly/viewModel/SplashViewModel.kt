package com.example.monthly.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.monthly.data.dataclass.User
import com.example.monthly.data.db.UserDatabase
import com.example.monthly.data.repository.UserRepository

// ViewModel은 DB에 직접 접근하지 않아야 함. Repository 에서 데이터 통신 하도록 구현.
class SplashViewModel(application: Application) : AndroidViewModel(application) {
    val getUserData : LiveData<List<User>>? = null
    private val repository: UserRepository

    private val _currentData = MutableLiveData<List<User>>()
    val currentData : LiveData<List<User>>
        get() = _currentData

    init {
        val userDao = UserDatabase.getInstance(application).userDAO
        repository = UserRepository(userDao)
        _currentData.value = repository.getUserData.value
    }
}
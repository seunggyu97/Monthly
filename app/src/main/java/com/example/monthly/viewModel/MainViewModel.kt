package com.example.monthly.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.monthly.data.dataclass.User
import com.example.monthly.data.db.UserDatabase
import com.example.monthly.data.repository.UserRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>

    init {
        val userDao = UserDatabase.getInstance(application).userDAO
        repository = UserRepository(userDao)
        user = repository.getUser
    }

}
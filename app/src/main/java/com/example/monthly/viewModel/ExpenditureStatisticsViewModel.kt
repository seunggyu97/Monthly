package com.example.monthly.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.monthly.data.dataclass.User
import com.example.monthly.data.db.UserDatabase
import com.example.monthly.data.repository.UserRepository
import java.util.*

class ExpenditureStatisticsViewModel(application: Application) : AndroidViewModel(application) {
    private val _cal = GregorianCalendar()
    private val _current = Calendar.getInstance()
    private val repository: UserRepository
    private val _user = MutableLiveData<User>()
    private var _month = MutableLiveData<Int>()

    val user: LiveData<User>
    val month : LiveData<Int>
        get() = _month

    init {
        val userDao = UserDatabase.getInstance(application).userDAO
        repository = UserRepository(userDao)
        user = repository.getUser

        _month.value = _current.get(Calendar.MONTH)
        _cal.set(Calendar.YEAR, _current.get(Calendar.YEAR))
        _cal.set(Calendar.MONTH, _current.get(Calendar.MONTH))
        _cal.set(Calendar.DATE, _current.get(Calendar.DATE))
    }

    fun changeToPrevMonth() {
        _cal.add(Calendar.MONTH, -1)
        _month.value = _cal.get(Calendar.MONTH)
    }

    fun changeToNextMonth() {
        _cal.add(Calendar.MONTH, +1)
        _month.value = _cal.get(Calendar.MONTH)
    }


    fun getDayOfWeek() : Int {
        return _cal.get(Calendar.DAY_OF_WEEK)
    }

    fun getReferenceDate() : String {
        return user.value?.referenceDate.toString()
    }
}
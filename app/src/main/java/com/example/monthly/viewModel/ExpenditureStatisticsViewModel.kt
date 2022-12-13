package com.example.monthly.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.monthly.GlobalApplication
import com.example.monthly.data.dataclass.DailyAccount
import com.example.monthly.data.dataclass.User
import com.example.monthly.data.db.DailyAccountDatabase
import com.example.monthly.data.db.UserDatabase
import com.example.monthly.data.repository.DailyAccountRepository
import com.example.monthly.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class ExpenditureStatisticsViewModel(application: Application) : AndroidViewModel(application) {

    val _cal = GregorianCalendar()
    private val _current = Calendar.getInstance()
    private val repository: DailyAccountRepository
    private val _dailyAccount = MutableLiveData<DailyAccount>()
    private var _month = MutableLiveData<Int>()
    private var _mdate = MutableLiveData<String>()
    private var _mMonth = MutableLiveData<String>()
    private var _price = MutableLiveData<Int>()
    private var _totalPrice = MutableLiveData<Int>()
    private var _category = MutableLiveData<String>()
    private var _memo = MutableLiveData<String>()
    private val userRepository: UserRepository

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>

    val dailyAccount: LiveData<List<DailyAccount>>
    val month: LiveData<Int>
        get() = _month
    val mDate: LiveData<String> = _mdate
    val mMonth: LiveData<String> = _mMonth
    val price: LiveData<Int> = _price
    val totalPrice: LiveData<Int> = _totalPrice
    val category: LiveData<String> = _category
    val memo: LiveData<String> = _memo

    init {

        val userDao = UserDatabase.getInstance(application).userDAO
        userRepository = UserRepository(userDao)
        user = userRepository.getUser

        val dailyAccountDao = DailyAccountDatabase.getInstance(application).dailyAccountDAO
        repository = DailyAccountRepository(dailyAccountDao)
        dailyAccount = repository.accounts

        _month.value = _current.get(Calendar.MONTH)
        _cal.set(Calendar.YEAR, _current.get(Calendar.YEAR))
        _cal.set(Calendar.MONTH, _current.get(Calendar.MONTH))
        _cal.set(Calendar.DATE, _current.get(Calendar.DATE))
    }

    fun changeToPrevMonth() {
        _cal.set(Calendar.MONTH, _month.value!!.toInt())
        _cal.add(Calendar.MONTH, -1)
        _month.value = _cal.get(Calendar.MONTH)
        _mMonth.value = _cal.get(Calendar.YEAR).toString() + "-" + (_cal.get(Calendar.MONTH)+1)
        Log.e("myTag", _mMonth.value.toString())
    }

    fun changeToNextMonth() {
        _cal.set(Calendar.MONTH, _month.value!!.toInt())
        _cal.add(Calendar.MONTH, +1)
        _month.value = _cal.get(Calendar.MONTH)
        _mMonth.value = _cal.get(Calendar.YEAR).toString() + "-" + (_cal.get(Calendar.MONTH)+1)
        Log.e("myTag", _mMonth.value.toString())
    }

    fun setTotalPrice(price: Int) {
        _totalPrice.value = price
    }

    fun setDate(date: String) {
        _mdate.value = date
    }

    fun setMonth(month: String) {
        _mMonth.value = month
    }

    fun setMonthVal(month: Int) {
        _month.value = month
    }

    fun setPrice(price: Int) {
        _price.value = price
    }

    fun setCategory(category: String) {
        _category.value = category
    }

    fun setMemo(memo: String) {
        _memo.value = memo
    }

    fun saveDatabase() {
        insert(
            DailyAccount(
                0,
                _mMonth.value.toString(),
                _mdate.value.toString(),
                _price.value!!.toInt(),
                _category.value.toString(),
                _memo.value.toString()
            )
        )
    }

    fun getAllByMonth(month: String): LiveData<List<DailyAccount>?>{
        return repository.getAllByMonth(month)
    }

    private fun insert(dailyAccount: DailyAccount) = viewModelScope.launch(Dispatchers.IO) {
        val newRowId = repository.insert(dailyAccount)
        withContext(Dispatchers.Main) {
            Log.e("MyTag", "DB Inserted, daily_id = $newRowId")
        }
    }

}
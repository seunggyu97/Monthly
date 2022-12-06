package com.example.monthly.viewModel

import android.app.Application
import androidx.lifecycle.*

//class InitViewModel(private val repository: UserRepository, application: Application) : AndroidViewModel(application) {
class InitViewModel(application: Application) : AndroidViewModel(application) {

    private val _inputName = MutableLiveData<String>()
    private val _inputDay = MutableLiveData<String>()
    private val _inputLimitValue = MutableLiveData<String>()

    val inputName : LiveData<String>
        get() = _inputName
    val inputDay : LiveData<String>
        get() = _inputDay
    val inputLimitValue : LiveData<String>
        get() = _inputLimitValue

    init{
        _inputDay.value = "선택"
    }

    fun setName(name: String) {
        _inputName.value = name
    }

    fun setDay(day: String){
        _inputDay.value = day
    }

    fun setLimitValue(limit: String){
        _inputLimitValue.value = limit
    }

//    fun saveDatabase() {
//        insert(User(0, _inputName.value.toString(), _inputDay.value?.toInt() ?: 1, _inputLimitValue.value?.toInt() ?: 1000))
//    }

    fun getLimitValue(): String? = _inputLimitValue.value
    fun getReferenceDate(): String? = _inputDay.value
    fun getName(): String? = _inputName.value

//    private fun insert(user: User) = viewModelScope.launch(Dispatchers.IO) {
//        val newRowId = repository.insert(user)
//        withContext(Dispatchers.Main) {
////            Log.e("MyTag", )
//        }
//    }
}
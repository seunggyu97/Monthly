package com.example.monthly.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InitViewModel() : ViewModel() {
//    private var total = MutableLiveData<Int>()
//    val totalData : LiveData<Int>
//        get() = total

    val inputName = MutableLiveData<String>()
    val inputDay = MutableLiveData<String>()
    val inputLimitValue = MutableLiveData<String>()

//    init {
//        total.value = startingTotal
//    }

//    fun setTotal(){
//        val intInput: Int = inputText.value!!.toInt()
//        total.value =(total.value)?.plus(intInput)
//    }
}
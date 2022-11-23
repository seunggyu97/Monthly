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

    fun dismiss(){
        // Todo : Dialog 닫기 로직 추가할 것
    }

    fun inputComplete(){
        // Todo : 입력완료 onClick 추가할 것
    }

    fun resign(){
        // Todo : 다시서명 기능 구현할 것
    }
}
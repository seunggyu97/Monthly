package com.example.monthly.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monthly.enumClass.FocusedEditTextType

class InitViewModel() : ViewModel() {

    private val _inputName = MutableLiveData<String>()
    private val _inputDay = MutableLiveData<String>()
    private val _inputLimitValue = MutableLiveData<Int>()

    val inputName : LiveData<String>
        get() = _inputName
    val inputDay : LiveData<String>
        get() = _inputDay
    val inputLimitValue : LiveData<Int>
        get() = _inputLimitValue

    init{
        _inputDay.value = "선택"
    }

    fun setName(name: String) {
        _inputName.value = name
    }

    fun setDay(day: String){
        _inputDay.value = day
        Log.e("MyTag","setDay실행")
    }

    fun setLimitValue(limit: String){
        _inputLimitValue.value = limit.toInt()
    }

    fun getLimitValue(): Int? = _inputLimitValue.value
    fun getReferenceDate(): String? = _inputDay.value

    fun dismiss(){
        // Todo : Dialog 닫기 로직 추가할 것
    }

    fun inputComplete(){
        // Todo : 입력완료 onClick 추가할 것
    }

    fun resign(){
        // Todo : 다시서명 기능 구현할 것
    }

    fun saveImage(){
        // Todo : 다짐서 이미지 저장 기능 구현할 것
    }

    fun initConfirm(){
        // Todo : activity_init 확인 버튼 기능 구현할 것
    }

    fun initComplete(){
        // Todo : activity_initfinal 완료 버튼 기능 구현할 것
    }
}
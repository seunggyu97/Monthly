package com.example.monthly.ui

import BottomSheetDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.R
import com.example.monthly.databinding.ActivityInitBinding
import com.example.monthly.ui.dialogs.InitCheckCustomDialog
import com.example.monthly.ui.dialogs.InitDialogInterface
import com.example.monthly.viewModel.InitViewModel

class InitActivity : AppCompatActivity(), InitDialogInterface {
    private lateinit var binding: ActivityInitBinding
    private lateinit var initViewModel: InitViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        addTextChangedListener()
    }

    fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_init)
        initViewModel = ViewModelProvider(this).get(InitViewModel::class.java)

        // viewModel의 inputName 값에 변동이 생길때마다 실행
        initViewModel.inputName.observe(this) {
            Log.e("MyTag", "inputNameObserved")
            if (it.isNotEmpty()) binding.btnConfirm.visibility = View.VISIBLE
            else binding.btnConfirm.visibility = View.GONE
        }

        // viewModel의 inputDay 값에 변동이 생길때마다 실행
        initViewModel.inputDay.observe(this) {
            Log.e("MyTag", "inputDayObserved")
            binding.tvReferenceDate.text = initViewModel.getReferenceDate().toString()
            if (!it.equals("선택")) showLimitValue()
        }

        binding.apply {

            // 확인 버튼
            btnConfirm.setOnClickListener {
                tvNameError.visibility = View.GONE
                tvReferenceDateError.visibility = View.GONE
                tvLimitValueError.visibility = View.GONE
                if (etName.isFocused) {
                    // 이름에 포커스 되어있는 상태라면 기준일 레이아웃을 보이게 한다.
                    binding.llReferenceDate.visibility = View.VISIBLE
                    binding.tvGuide.text = getString(R.string.init_guide2)
                    binding.tvTop.text = getString(R.string.init_top2)
                    showBottomSheet()
                    Log.e("MyTag", "이름 포커스")
                }
                // 기준일 입력칸에 포커스 되어있는 상태라면 한도금액 레이아웃을 보이게 한 후 한도 입력칸에 포커스 이동
                else if (tvReferenceDate.isFocused) {
                    binding.llLimitValue.visibility = View.VISIBLE
                    binding.etLimitValue.requestFocus()
                    Log.e("MyTag", "기준일 포커스")

                } else if (etLimitValue.isFocused) {
                    if (checkAllInput()){
                        initViewModel.setDay(binding.tvReferenceDate.text.toString())
                        initViewModel.setLimitValue(binding.etLimitValue.text.toString())
                        val initFinalCustomDialog = InitCheckCustomDialog (this@InitActivity,
                            initViewModel.getName().toString(),
                            initViewModel.getReferenceDate().toString(),
                            initViewModel.getLimitValue().toString())
                        initFinalCustomDialog.show(supportFragmentManager, "init_check_custom_dialog")
                    }
                }
            }

            // 기준일 선택 버튼
            clReferenceDate.setOnClickListener {
                showBottomSheet()
            }
        }
    }

    fun checkAllInput(): Boolean {
        var flag: Boolean = true
        binding.apply {
            if (etName.text.isEmpty()) {
                flag = false
                tvNameError.visibility = View.VISIBLE
            }
            if (tvReferenceDate.text.equals("선택") || tvReferenceDate.text == null) {
                flag = false
                tvReferenceDateError.visibility = View.VISIBLE
            }
            if (etLimitValue.text.toString().toInt() < 1000 || etLimitValue.text.isEmpty()) {
                flag = false
                tvLimitValueError.visibility = View.VISIBLE
            }
        }
        return flag
    }

    fun showLimitValue() {
        binding.llLimitValue.visibility = View.VISIBLE
        binding.etLimitValue.requestFocus()
        Log.e("MyTag", "기준일 포커스")
        binding.tvGuide.text = getString(R.string.init_guide3)
        binding.tvTop.text = getString(R.string.init_top3)
    }

    fun showBottomSheet() {
        val bottomSheet = BottomSheetDialog(this)
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    fun addTextChangedListener() {
        // 이름 입력칸의 내용이 바뀔 때 마다 viewModel에 저장
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //텍스트를 입력 후
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 전
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 중
                initViewModel.setName(binding.etName.text.toString())
            }
        })

        //  한도금액의 내용이 바뀔 때 마다 한글형식 출력(ex: 오만오천원)
        binding.etLimitValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //텍스트를 입력 후
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 전
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 중
//                initViewModel.setLimitValue(binding.etLimitValue.text.toString())
            }
        })
    }

    // 기준일 bottomSheetDialog 입력 버튼 클릭
    override fun onCompleteButtonClicked(content: String) {
        initViewModel.setDay(content)
    }

    // initCheckCustomDialog 입력완료 버튼 클릭
    override fun onFinishButtonClicked() {
        Log.e("MyTag", "Finishedddd")
    }
}
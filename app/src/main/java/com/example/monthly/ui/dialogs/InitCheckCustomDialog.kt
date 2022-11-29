package com.example.monthly.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.monthly.databinding.ActivityInitBinding
import com.example.monthly.databinding.DialogBottomsheetBinding
import com.example.monthly.databinding.DialogInitcheckBinding

class InitCheckCustomDialog(
    initDialogInterface: InitDialogInterface,
    name: String, referenceDate: String, limitValue: String
) : DialogFragment() {

    // 데이터 바인딩
    private var binding: DialogInitcheckBinding? = null

    private var initDialogInterface: InitDialogInterface? = null

    private var name: String? = null
    private var referenceDate: String? = null
    private var limitValue: String? = null

    init {
        this.initDialogInterface = initDialogInterface
        this.name = name
        this.referenceDate = referenceDate
        this.limitValue = limitValue
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogInitcheckBinding.inflate(inflater, container, false)
        val view = binding!!.root

        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

//        binding.confirmTextView.text = text

        // 취소 버튼 클릭
//        binding.noButton.setOnClickListener {
//            dismiss()
//        }

        // 확인 버튼 클릭
//        binding.yesButton.setOnClickListener {
//            this.confirmDialogInterface?.onYesButtonClick(id!!)
//            dismiss()
//        }

        binding?.apply {
            btnClose.setOnClickListener {
                dismiss()
            }
            btnFinish.setOnClickListener {
                initDialogInterface?.onFinishButtonClicked()
                dismiss()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
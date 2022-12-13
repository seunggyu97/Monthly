package com.example.monthly.ui.dialogs

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.monthly.databinding.DialogInitcheckBinding
import com.example.monthly.ui.CanvasView
import com.example.monthly.util.AppendCommaToPriceValue
import com.example.monthly.viewModel.InitViewModel


class InitCheckCustomDialog(
    initDialogInterface: InitDialogInterface,
    name: String, referenceDate: String, limitValue: String
) : DialogFragment() {

    // 데이터 바인딩
    private var binding: DialogInitcheckBinding? = null

    private var initDialogInterface: InitDialogInterface? = null

    private val sharedInitViewModel by activityViewModels<InitViewModel>()
    private var name: String? = null
    private var referenceDate: String? = null
    private var limitValue: String? = null

    lateinit var canvasView: CanvasView

    init {
        this.initDialogInterface = initDialogInterface
        this.name = name
        this.referenceDate = referenceDate
        this.limitValue = AppendCommaToPriceValue(limitValue.toInt())
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

        canvasView = binding!!.canvas
        binding!!.viewModel = sharedInitViewModel

        binding?.apply {
            btnClose.setOnClickListener {
                dismiss()
            }
            btnFinish.setOnClickListener {
                // canvasView클래스의 path를 가져오고 인터페이스를 통해 전달
//                val mPath: Path = canvasView.getCanvasPath()
                val bitmap = saveBitmap()
                initDialogInterface?.onFinishButtonClicked(bitmap)
                dismiss()
            }

            // 다시서명 버튼 클릭 -> 사인 canvas 초기화
            llCanvasClear.setOnClickListener {
                canvasView.ClearCanvas()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun saveBitmap(): Bitmap {
        // 1. 캐쉬(Cache)를 허용시킨다.
        // 2. 그림을 Bitmap 으로 저장.
        // 3. 캐쉬를 막는다.
        canvasView.setDrawingCacheEnabled(true) // 캐쉬허용
        // 캐쉬에서 가져온 비트맵을 복사해서 새로운 비트맵(스크린샷) 생성
        val bitmap: Bitmap = Bitmap.createBitmap(canvasView.getDrawingCache())
        canvasView.setDrawingCacheEnabled(false) // 캐쉬닫기

        return bitmap
    }
}
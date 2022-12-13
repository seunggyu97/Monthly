package com.example.monthly.ui.dialogs

import android.R
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.monthly.databinding.DialogDailyInsertBinding
import com.example.monthly.enumClass.ServiceType
import com.example.monthly.ui.CanvasView
import com.example.monthly.ui.ExpenditureStatisticsActivity


class DailyInsertCustomDialog(
    dailyInsertDialogInterface: DailyInsertDialogInterface,
    _date: String, activity: ExpenditureStatisticsActivity
) : DialogFragment() {

    // 데이터 바인딩
    private var binding: DialogDailyInsertBinding? = null

    private var dailyInsertDialogInterface: DailyInsertDialogInterface? = null

    private var date: String = _date
    private var activity: ExpenditureStatisticsActivity = activity
    lateinit var canvasView: CanvasView

    init {
        this.dailyInsertDialogInterface = dailyInsertDialogInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogDailyInsertBinding.inflate(inflater, container, false)
        val view = binding!!.root

        val items = arrayOf("편의점", "교통", "카페", "주유", "병원", "교육", "식비", "여가", "유흥", "기타")

        var category = ServiceType.CONVENIENCE
        val myAdapter = ArrayAdapter(activity, R.layout.simple_spinner_dropdown_item, items)

        binding!!.categorySpinner.adapter = myAdapter
        binding!!.categorySpinner.setSelection(0)
        binding!!.categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {

                    when (position) {
                        0 -> {
                            category = ServiceType.CONVENIENCE
                        }
                        1 -> {
                            category = ServiceType.TRANSPORT
                        }
                        2 -> {
                            category = ServiceType.CAFE
                        }
                        3 -> {
                            category = ServiceType.GAS
                        }
                        4 -> {
                            category = ServiceType.HOSPITAL
                        }
                        5 -> {
                            category = ServiceType.EDUCATION
                        }
                        6 -> {
                            category = ServiceType.FOOD
                        }
                        7 -> {
                            category = ServiceType.LEISURE
                        }
                        8 -> {
                            category = ServiceType.ENTERTAINMENT
                        }
                        else -> {
                            category = ServiceType.DEFAULT
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding?.apply {

            btnDailyInsertCancle.setOnClickListener {
                dismiss()
            }
            btnDailyInsertComplete.setOnClickListener {
                dailyInsertDialogInterface?.onFinishButtonClicked(
                    date,
                    Integer.parseInt(etDailyInsertPrice.text.toString()),
                    category,
                    etDailyInsertMemo.text.toString()
                )
                dismiss()
            }

            tvDailyInsertDate.text = date

        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
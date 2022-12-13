package com.example.monthly.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monthly.Constant
import com.example.monthly.PasswordInputActivity
import com.example.monthly.R
import com.example.monthly.adapter.ExpenditureListAdapter
import com.example.monthly.databinding.ActivityExpenditureStatisticsBinding
import com.example.monthly.ui.dialogs.InitCheckCustomDialog
import com.example.monthly.viewModel.ExpenditureStatisticsViewModel
import com.example.monthly.viewModel.InitViewModel
import java.io.ByteArrayOutputStream


class ExpenditureStatisticsActivity : PasswordInputActivity() {
    private lateinit var binding: ActivityExpenditureStatisticsBinding
    private lateinit var expenditureStatisticsViewModel: ExpenditureStatisticsViewModel
    private val expenditureListAdapter: ExpenditureListAdapter by lazy {
        ExpenditureListAdapter()
    }

    private val dayOfWeekArray = arrayOf("", "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expenditure_statistics)
        expenditureStatisticsViewModel = ViewModelProvider(this)[ExpenditureStatisticsViewModel::class.java]
        val dataSet = arrayListOf<Constant.Expenditure>().apply {
            add(Constant.Expenditure(
                "13일",
                dayOfWeekArray[3],
                "35,000"))

            add(Constant.Expenditure(
                "12일",
                dayOfWeekArray[2],
                "55,000"))

            add(Constant.Expenditure(
                "11일",
                dayOfWeekArray[1],
                "10,000"))

            add(Constant.Expenditure(
                "10일",
                dayOfWeekArray[7],
                "5,000"))

            add(Constant.Expenditure(
                "9일",
                dayOfWeekArray[6],
                "35,000"))

            add(Constant.Expenditure(
                "8일",
                dayOfWeekArray[5],
                "35,000"))
        }
        binding.apply {
            viewModel = expenditureStatisticsViewModel
            activity = this@ExpenditureStatisticsActivity
            title = getString(R.string.statistics_title)

            ivHelp.setOnClickListener {
                if (ivHelpguide.isVisible) {
                    ivHelpguide.visibility = View.INVISIBLE
                    ivHelpguideBody.visibility = View.INVISIBLE
                }
                else {
                    ivHelpguide.visibility = View.VISIBLE
                    ivHelpguideBody.visibility = View.VISIBLE
                }
            }
            ivPrevMonth.setOnClickListener {
                expenditureStatisticsViewModel.changeToPrevMonth()
            }
            ivNextMonth.setOnClickListener {
                expenditureStatisticsViewModel.changeToNextMonth()
            }
            tvReferenceDate.text = expenditureStatisticsViewModel.user.value?.referenceDate.toString()
        }

        expenditureStatisticsViewModel.month.observe(this) {
            it?.let{
                Log.e("mytag",it.toString())
                binding.tvExpenditureMonth.text = String.format("${it+1}월")
            }
        }

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = expenditureListAdapter
        }
        expenditureListAdapter.submitList(dataSet)
    }

}

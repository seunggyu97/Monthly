package com.example.monthly.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monthly.Constant
import com.example.monthly.GlobalApplication
import com.example.monthly.PasswordInputActivity
import com.example.monthly.R
import com.example.monthly.adapter.ExpenditureListAdapter
import com.example.monthly.data.dataclass.DailyAccount
import com.example.monthly.data.dataclass.DailyCalendar
import com.example.monthly.databinding.ActivityExpenditureStatisticsBinding
import com.example.monthly.enumClass.ServiceType
import com.example.monthly.ui.dialogs.DailyInsertCustomDialog
import com.example.monthly.ui.dialogs.DailyInsertDialogInterface
import com.example.monthly.util.AppendCommaToPriceValue
import com.example.monthly.viewModel.ExpenditureStatisticsViewModel
import java.util.*


class ExpenditureStatisticsActivity : PasswordInputActivity(), DailyInsertDialogInterface {
    private lateinit var binding: ActivityExpenditureStatisticsBinding
    private lateinit var expenditureStatisticsViewModel: ExpenditureStatisticsViewModel
    private lateinit var adapter: ExpenditureListAdapter
    private lateinit var charTmpArr: List<String>
    private val expenditureListAdapter: ExpenditureListAdapter by lazy {
        ExpenditureListAdapter()
    }
    private var dateString = ""
    private var hm = HashMap<ServiceType, String>()
    private lateinit var dailyCalendar: DailyCalendar
    private val dayOfWeekArray = arrayOf("", "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일")
    var dataSet = arrayListOf<Constant.Expenditure>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expenditure_statistics)
        expenditureStatisticsViewModel =
            ViewModelProvider(this)[ExpenditureStatisticsViewModel::class.java]

        hm[ServiceType.CONVENIENCE] = "편의점"
        hm[ServiceType.TRANSPORT] = "교통"
        hm[ServiceType.CAFE] = "카페"
        hm[ServiceType.GAS] = "주유"
        hm[ServiceType.HOSPITAL] = "병원"
        hm[ServiceType.EDUCATION] = "교육"
        hm[ServiceType.FOOD] = "식비"
        hm[ServiceType.LEISURE] = "여가"
        hm[ServiceType.ENTERTAINMENT] = "유흥"
        hm[ServiceType.DEFAULT] = "기타"

        var cal = Calendar.getInstance()
        expenditureStatisticsViewModel.setMonth("${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH) + 1}")

        //RecyclerView
        val recyclerView = binding.recyclerView
        setRecyclerView(recyclerView)

        // date LiveData 변경 감지
        expenditureStatisticsViewModel.mMonth.observe(this, androidx.lifecycle.Observer {
            Log.d("date", it.toString())
            charTmpArr = it.split("-")
            val sb = StringBuilder()
            sb.append(charTmpArr[0]).append('-').append(charTmpArr[1])
            Log.e("myTag", sb.toString())
            expenditureStatisticsViewModel.getAllByMonth(sb.toString())
                .observe(this) { dailyList ->
                    Log.d("dailyList", dailyList.toString())
                    if (dailyList != null) {
                        // Adapter 데이터 갱신
                        expenditureStatisticsViewModel.setMonthVal(charTmpArr[1].toInt() - 1)

                        setExpendData(dailyList)
                        // 날짜 순으로 정렬하여 adapter에 집어넣는다
                        adapter.setDailyAccount(
                            dailyList.sortedWith(
                                compareByDescending(
                                    DailyAccount::date
                                )
                            )
                        )
                        adapter.notifyDataSetChanged()
                    }
                }
        })

        expenditureStatisticsViewModel.user.observe(this) {
            it?.let {
                val referenceDate = it.referenceDate.toString() + "일"
                binding.tvReferenceDate.text = referenceDate
            }
        }

        binding.apply {
            viewModel = expenditureStatisticsViewModel
            activity = this@ExpenditureStatisticsActivity
            title = getString(R.string.statistics_title)

            ivHelp.setOnClickListener {
                if (ivHelpguide.isVisible) {
                    ivHelpguide.visibility = View.INVISIBLE
                    ivHelpguideBody.visibility = View.INVISIBLE
                } else {
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
            btnAddExpend.setOnClickListener {
                val cal = Calendar.getInstance()    //캘린더뷰 만들기

                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        dateString = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                        var tmp = dayOfMonth.toString()
                        if (dayOfMonth < 10) tmp = "0$tmp"
                        expenditureStatisticsViewModel.setDate("${year}-${month + 1}-$tmp")
                        expenditureStatisticsViewModel.setMonth("${year}-${month + 1}")
                        dailyCalendar = DailyCalendar(year, month, dayOfMonth)
                        showDetailDialog(dateString)
                    }
                DatePickerDialog(
                    this@ExpenditureStatisticsActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        expenditureStatisticsViewModel.month.observe(this) {
            it?.let {
                Log.e("mytag", it.toString())
                binding.tvExpenditureMonth.text = String.format("${it + 1}월")
            }
        }

        expenditureStatisticsViewModel.totalPrice.observe(this) {
            it?.let {
                val cal = Calendar.getInstance()
                var average = 0
                var recommend = 0
                binding.tvTotalExpend.text = AppendCommaToPriceValue(it)

                binding.tvDailyAverage.text =
                    AppendCommaToPriceValue(it / cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                average = it / cal.getActualMaximum(Calendar.DAY_OF_MONTH)

                binding.tvDailyRecommend.visibility = View.GONE
                binding.tvTitleDailyRecommend.visibility = View.GONE
                binding.tvStringDailyRecommendWon.visibility = View.GONE
                binding.tvDailyAverage.setTextColor(getColor(R.color.font_black))
                binding.tvDailyAverageWon.setTextColor(getColor(R.color.font_black))
                binding.tvTitleDailyAverage.setTextColor(getColor(R.color.font_black))
                binding.ivHelp.visibility = View.GONE
                binding.ivHelpguide.visibility = View.GONE
                binding.ivHelpguideBody.visibility = View.GONE

                if (expenditureStatisticsViewModel._cal.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
                    if (expenditureStatisticsViewModel._cal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) {
                        // 현재달을 표시중일때

                        GlobalApplication.prefs.setInt(
                            "currentMonthExpend",
                            it
                        ) // SharedPref에 이번달지출금액 저장(MainActivity 표시용)


                        binding.tvDailyAverage.text =
                            AppendCommaToPriceValue(it / cal.get(Calendar.DAY_OF_MONTH))
                        val remainDay =
                            cal.getActualMaximum(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH)
                        val limitValue = GlobalApplication.prefs.getInt("limitValue")
                        binding.tvDailyRecommend.text = AppendCommaToPriceValue(
                            (limitValue - it) / remainDay
                        )
                        recommend = (limitValue - it) / remainDay

                        binding.tvDailyRecommend.visibility = View.VISIBLE
                        binding.tvTitleDailyRecommend.visibility = View.VISIBLE
                        binding.tvStringDailyRecommendWon.visibility = View.VISIBLE
                        binding.ivHelp.visibility = View.VISIBLE
                        binding.ivHelpguide.visibility = View.INVISIBLE
                        binding.ivHelpguideBody.visibility = View.INVISIBLE

                        if (recommend < average) {
                            binding.tvDailyAverage.setTextColor(getColor(R.color.error))
                            binding.tvTitleDailyAverage.setTextColor(getColor(R.color.error))
                            binding.tvDailyAverageWon.setTextColor(getColor(R.color.error))
                        } else {
                            binding.tvDailyAverage.setTextColor(getColor(R.color.font_black))
                            binding.tvTitleDailyAverage.setTextColor(getColor(R.color.font_black))
                            binding.tvDailyAverageWon.setTextColor(getColor(R.color.font_black))
                        }
                    }
                }

            }
        }

    }

    fun showDetailDialog(date: String) {
        val dailyInsertCustomDialog = DailyInsertCustomDialog(
            this@ExpenditureStatisticsActivity,
            date, this@ExpenditureStatisticsActivity
        )
        dailyInsertCustomDialog.show(supportFragmentManager, "daily_insert_custom_dialog")
    }

    fun setExpendData(dailyList: List<DailyAccount>) {
        var expendValue = 0
        for (i in dailyList) {
            expendValue += i.dailySpent
        }

        expenditureStatisticsViewModel.setTotalPrice(expendValue)
    }

    private fun setRecyclerView(recyclerView: RecyclerView) {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = ExpenditureListAdapter()
        recyclerView.adapter = adapter
    }

    override fun onFinishButtonClicked(
        date: String,
        price: Int,
        category: ServiceType,
        memo: String
    ) {
        Log.e("myTag", "$date $price ${hm[category]} $memo")
        expenditureStatisticsViewModel.setPrice(price)
        expenditureStatisticsViewModel.setCategory(hm[category].toString())
        expenditureStatisticsViewModel.setMemo(memo)
        expenditureStatisticsViewModel.setMonthVal(charTmpArr[1].toInt() - 1)


        expenditureStatisticsViewModel.saveDatabase()
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out_down)
        }
    }
}

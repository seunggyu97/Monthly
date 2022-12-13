package com.example.monthly.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.monthly.Constant
import com.example.monthly.databinding.ActivityExpenditureStatisticsBinding
import com.example.monthly.databinding.ItemDailyExpenditureBinding

class ExpenditureViewHolder(private val binding: ItemDailyExpenditureBinding) :

    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Constant.Expenditure) {
        with(binding) {
            tvItemExpenditureDate.text = data.day
            tvItemExpenditureDayofweek.text = data.dayOfWeek
            tvItemDailyTotalExpenditure.text = data.totalValue
        }
    }

}
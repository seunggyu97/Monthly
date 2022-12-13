package com.example.monthly.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.monthly.Constant

class ExpenditureDiffCallback : DiffUtil.ItemCallback<Constant.Expenditure>() {

    override fun areItemsTheSame(oldItem: Constant.Expenditure, newItem: Constant.Expenditure): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Constant.Expenditure, newItem: Constant.Expenditure): Boolean {
        return oldItem == newItem
    }
}
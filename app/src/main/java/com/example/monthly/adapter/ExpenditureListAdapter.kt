package com.example.monthly.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.monthly.Constant
import com.example.monthly.databinding.ItemDailyExpenditureBinding
import com.example.monthly.diffUtil.ExpenditureDiffCallback
import com.example.monthly.viewHolder.ExpenditureViewHolder

class ExpenditureListAdapter : ListAdapter<Constant.Expenditure, RecyclerView.ViewHolder>(ExpenditureDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = ExpenditureViewHolder(
            ItemDailyExpenditureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ExpenditureViewHolder) {
            val expenditure = getItem(position) as Constant.Expenditure
            holder.bind(expenditure)
        }
    }
}
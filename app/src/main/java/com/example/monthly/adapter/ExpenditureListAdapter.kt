package com.example.monthly.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.monthly.Constant
import com.example.monthly.data.dataclass.DailyAccount
import com.example.monthly.databinding.ItemDailyExpenditureBinding
import com.example.monthly.diffUtil.ExpenditureDiffCallback
import com.example.monthly.util.AppendCommaToPriceValue

//import com.example.monthly.viewHolder.ExpenditureViewHolder


class ExpenditureListAdapter : RecyclerView.Adapter<ExpenditureListAdapter.ViewHolder>(){

    private val dailyList = ArrayList<DailyAccount>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenditureListAdapter.ViewHolder {
        val binding = ItemDailyExpenditureBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenditureListAdapter.ViewHolder, position: Int) {
        val todoEntity = dailyList[position]
        holder.setTodoListUI(todoEntity,position)
    }

    override fun getItemCount(): Int {
        return dailyList.size
    }

    fun setDailyAccount(daily: List<DailyAccount>?){
        if (daily != null){
            dailyList.clear()
            dailyList.addAll(daily)
            println(dailyList)
        } else {
            dailyList.clear()
        }
    }

    inner class ViewHolder(private val binding: ItemDailyExpenditureBinding) : RecyclerView.ViewHolder(binding.root){
        fun setTodoListUI(daily: DailyAccount, position: Int){
            binding.tvItemExpenditureDate.text = daily.date
//            binding.tvItemExpenditureDayofweek.text = "$position"
            binding.tvItemInnerPlace.text = daily.dailyMemo
            binding.tvItemInnerCategory.text = daily.category
            binding.tvItemInnerValue.text = AppendCommaToPriceValue(daily.dailySpent)
        }
    }
}
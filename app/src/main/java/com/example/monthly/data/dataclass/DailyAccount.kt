package com.example.monthly.data.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param id : pk값
 * @param date : 날짜
 * @param dailySpent : 해당 날짜에 쓴 금액
 * @param category : 카테고리
 * @param dailyMemo : 메모(사용처)
 */
@Entity(tableName = "daily_account_table")
data class DailyAccount(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "daily_id")
    val id: Long,

    @ColumnInfo(name = "daily_month")
    var month: String,

    @ColumnInfo(name = "daily_day")
    var date: String,

    @ColumnInfo(name = "daily_spent_price")
    var dailySpent: Int,

    @ColumnInfo(name = "daily_category")
    var category: String,

    @ColumnInfo(name = "daily_memo")
    var dailyMemo: String
)

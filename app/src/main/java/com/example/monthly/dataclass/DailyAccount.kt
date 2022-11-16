package com.example.monthly.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

/**
 * @param id : pk값
 * @param date : 날짜
 * @param dailySpent : 해당 날짜에 쓴 금액
 */
@Entity(tableName = "daily_account_table")
data class DailyAccount (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "daily_id")
    val id: Long,

    @ColumnInfo(name= "daily_date")
    var date: Date,

    @ColumnInfo(name = "daily_spent_price")
    var dailySpent: Int,
)

package com.example.monthly.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param id : pk값
 * @param name : 사용자 이름
 * @param limit : 월 사용 한도 금액
 * @param referenceDate : 기준일 지정
 */
@Entity(tableName = "user_data_table")
data class User (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Long,

    @ColumnInfo(name= "user_name")
    var name: String,

    @ColumnInfo(name = "limit_amount")
    var limit: Int,

    @ColumnInfo(name = "reference_date")
    var referenceDate: Int
)

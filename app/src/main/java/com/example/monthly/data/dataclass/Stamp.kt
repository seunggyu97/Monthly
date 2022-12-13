//package com.example.monthly.data.dataclass
//
//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import java.sql.Date
//
///**
// * @param id : pk값
// * @param date : 스탬프 찍은 날짜
// * @param savedPrice : 해당 월에 절약한 금액
// */
//@Entity(tableName = "stamp_data_table")
//data class Stamp (
//
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "stamp_id")
//    val id: Long,
//
//    @ColumnInfo(name= "stamp_date")
//    var date: Date,
//
//    @ColumnInfo(name = "saved_price")
//    var savedPrice: Int,
//)

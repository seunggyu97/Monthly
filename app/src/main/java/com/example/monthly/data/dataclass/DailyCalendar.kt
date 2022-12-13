package com.example.monthly.data.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.monthly.enumClass.ServiceType
import java.sql.Date
import java.util.Calendar

data class DailyCalendar(

    val year: Int,

    var month: Int,

    var day: Int,
)

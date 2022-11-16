package com.example.monthly.repository

import com.example.monthly.dao.DailyAccountDAO
import com.example.monthly.dao.StampDAO
import com.example.monthly.dataclass.DailyAccount
import com.example.monthly.dataclass.Stamp
import com.example.monthly.dataclass.User

class DailyAccountRepository(private val dao : DailyAccountDAO) {

    val stamps = dao.getAllDaily()

    suspend fun insert(dailyAccount: DailyAccount): Long{
        return dao.insertDaily(dailyAccount)
    }

    suspend fun update(dailyAccount: DailyAccount): Int{
        return dao.updateDaily(dailyAccount)
    }

    suspend fun delete(dailyAccount: DailyAccount): Int{
        return dao.deleteDaily(dailyAccount)
    }

    suspend fun deleteAll(): Int{
        return dao.deleteAll()
    }
}
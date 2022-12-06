package com.example.monthly.data.repository

import com.example.monthly.data.dao.DailyAccountDAO
import com.example.monthly.data.dataclass.DailyAccount

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
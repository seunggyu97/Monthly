package com.example.monthly.data.repository

import androidx.lifecycle.LiveData
import com.example.monthly.data.dao.DailyAccountDAO
import com.example.monthly.data.dataclass.DailyAccount

class DailyAccountRepository(private val dao : DailyAccountDAO) {

    val accounts = dao.getAllDaily()

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

    fun getAllByMonth(month: String): LiveData<List<DailyAccount>?>{
        return dao.getAllByMonth(month)
    }
}
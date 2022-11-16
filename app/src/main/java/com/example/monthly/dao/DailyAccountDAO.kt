package com.example.monthly.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.monthly.dataclass.DailyAccount
import com.example.monthly.dataclass.Stamp
import com.example.monthly.dataclass.User


@Dao
interface DailyAccountDAO {

    @Insert
    suspend fun insertDaily(dailyAccount: DailyAccount) : Long

    @Update
    suspend fun updateDaily(dailyAccount: DailyAccount) : Int

    @Delete
    suspend fun deleteDaily(dailyAccount: DailyAccount) : Int

    @Query("DELETE FROM daily_account_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM daily_account_table")
    fun getAllDaily(): LiveData<List<DailyAccount>>

}
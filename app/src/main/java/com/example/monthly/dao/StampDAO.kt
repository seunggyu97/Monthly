package com.example.monthly.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.monthly.dataclass.Stamp
import com.example.monthly.dataclass.User


@Dao
interface StampDAO {

    @Insert
    suspend fun insertStamp(stamp: Stamp) : Long

    @Update
    suspend fun updateStamp(stamp: Stamp) : Int

    @Delete
    suspend fun deleteStamp(stamp: Stamp) : Int

    @Query("DELETE FROM stamp_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM stamp_data_table")
    fun getAllStamps(): LiveData<List<Stamp>>

}
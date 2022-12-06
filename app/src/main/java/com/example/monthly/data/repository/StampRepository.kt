package com.example.monthly.data.repository

import com.example.monthly.data.dao.StampDAO
import com.example.monthly.data.dataclass.Stamp

class StampRepository(private val dao : StampDAO) {

    val stamps = dao.getAllStamps()

    suspend fun insert(stamp: Stamp): Long{
        return dao.insertStamp(stamp)
    }

    suspend fun update(stamp: Stamp): Int{
        return dao.updateStamp(stamp)
    }

    suspend fun delete(stamp: Stamp): Int{
        return dao.deleteStamp(stamp)
    }

    suspend fun deleteAll(): Int{
        return dao.deleteAll()
    }
}